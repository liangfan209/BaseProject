package com.bq.utilslib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * 图片bitmap
 */
@SuppressLint("NewApi")
public class BitmapUtils {

    private static final String Tag = "BitmapUtils";
    public static final int MAX_SIZE = 1024;


    public static class ImageSize {
        public int width;
        public int height;
    }

    /**
     * 压缩图片并另存为新图
     * 【只需要指定最大宽度px，压缩过程中会按照原来的宽高比压缩】
     *
     * @param originFilePath 图片路径
     * @param reqWidth       最大宽度px
     * @param savedFilePath  新图存储路径
     * @param savedFileName  新图片名字
     * @return 新图绝对路径
     */
    public static String compressAndSavePicture(String originFilePath, int reqWidth, String savedFilePath, String savedFileName) {
        final Options options = new Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(originFilePath, options);
        int originWidth = options.outWidth;
        int originHeight = options.outHeight;
        LogUtils.d("BmpUtils.compressAndSavePicture():originWidth = " + originWidth);
        LogUtils.d("BmpUtils.compressAndSavePicture():originHeight = " + originHeight);
        if (originWidth > reqWidth) {
            int reqHeight = (reqWidth * originHeight) / originWidth;
            LogUtils.d("BmpUtils.compressAndSavePicture():reqWidth = " + reqWidth);
            LogUtils.d("BmpUtils.compressAndSavePicture():reqHeight = " + reqHeight);
            options.inSampleSize = calculateInSampleSize(originWidth, originHeight, reqWidth, reqHeight);
        } else {
            options.inSampleSize = 1;
        }
        LogUtils.d("BmpUtils.compressAndSavePicture():inSampleSize = " + options.inSampleSize);
        options.inJustDecodeBounds = false;
        //获取压缩位图 Decode bitmap with inSampleSize set
        Bitmap scaleBmp = BitmapFactory.decodeFile(originFilePath, options);
        if (scaleBmp == null) {
            return null;
        }
        //质量压缩【经测试指定目标大小没用】
        Bitmap massCompressBmp = getMassCompressBmp(scaleBmp, 300);
        //检查图片旋转角度
        Bitmap finalBmp = adjustPicRotate(massCompressBmp, originFilePath);
        //保存图片到本地，并返回路径
        String newPath = saveBmp(finalBmp, savedFilePath, savedFileName);

        //回收资源
        scaleBmp.recycle();
        massCompressBmp.recycle();
        System.gc();

        return newPath;
    }

    //计算inSampleSize
    private static int calculateInSampleSize(int originWidth, int originHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (originWidth > reqWidth || originHeight > reqHeight) {
            //Math.ceil(int value):表示取不小于value的最小整数
            int scaleWidth = (int) Math.ceil((originWidth * 1.0f) / reqWidth);
            int scaleHeight = (int) Math.ceil((originHeight * 1.0f) / reqHeight);
            inSampleSize = Math.max(scaleWidth, scaleHeight);
        }
        return inSampleSize;
    }

    /**
     * 将某个位图进行质量压缩【类型必须为JPEG】
     */
    public static Bitmap getMassCompressBmp(Bitmap image, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 40;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        // 循环判断如果压缩后图片是否大于最大值,大于继续压缩
        while (baos.toByteArray().length / 1024 > maxSize) {
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 检查图片角度是否旋转了90度，如果是则反转
     *
     * @param bitmap 需要旋转的图片位图
     * @param path   图片的路径
     */
    public static Bitmap adjustPicRotate(Bitmap bitmap, String path) {
        int degree = getPicRotate(path);
        if (degree > 0) {
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);

            int orientation =
                    exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            LogUtils.e("BmpUtils.getPicRotate():filePath = " + path + "\n获取图片旋转角度失败：" + e.toString());
        }
        return degree;
    }

    /**
     * 获取图片的真实后缀
     *
     * @param filePath 图片存储地址
     * @return 图片类型后缀
     */
    public static String getExtension(String filePath) {
        Options options = createOptions();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        String mimeType = options.outMimeType;
        return mimeType.substring(mimeType.lastIndexOf("/") + 1);
    }

    /**
     * A default size to use to increase hit rates when the required size isn't defined.
     * Currently 64KB.
     */
    public final static int DEFAULT_BUFFER_SIZE = 64 * 1024;

    /**
     * 创建一个图片处理Options
     *
     * @return {@link Options}
     */
    public static Options createOptions() {
        return new Options();
    }

    /**
     * 把一个{@link Options}进行参数复原操作，
     * 避免重复创建新的 {@link Options}
     *
     * @param options {@link Options}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void resetOptions(Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;

        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
            options.inBitmap = null;
            options.inMutable = true;
        }
    }

    /**
     * 保存图片
     *
     * @param bitmap   需要保存的图片
     * @param saveName 图片保存的名称
     * @return 返回保存后的图片地址
     */
    public static String saveBmp(Bitmap bitmap, String savePath, String saveName) {
        String resultPath = null;
        try {
            //保存位置
            File file = new File(savePath, saveName);
            if (file.exists())
                file.delete();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
            bitmap = null;
            System.gc();
            resultPath = file.getAbsolutePath();
        } catch (IOException e) {
            LogUtils.e("BmpUtils.saveBmp(): savePath = " + savePath + "\nsaveName = " + saveName + "\n保存图片失败：" + e.toString());
        }
        return resultPath;
    }

    public static final Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap greyBitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(greyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return greyBitmap;
    }

    public static Bitmap circular(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        // 保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // 圆形，所有只用一个
        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap readBitMap(Context context, int resId) {

        Options opt = new Options();

        opt.inPreferredConfig = Config.RGB_565;

        // 获取资源图片

        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is, null, opt);

    }

    public static final Bitmap alpha(Bitmap bitmap, int alpha) {
        float[] matrixItems = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, alpha / 255f, 0, 0, 0, 0, 0, 1};
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap alphaBitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(alphaBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix(matrixItems);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return alphaBitmap;
    }

    public static final Bitmap getVideoBitmap(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }
    // region BitmapFactory

    //TODO: 考虑大图
    public static Bitmap decodeByteArray(byte[] data, int offset, int length) {
        Bitmap bitmap = decodeByteArray(data, offset, length, null);

        return bitmap;
    }

    public static Bitmap decodeByteArray(byte[] data, int offset, int length, Options options) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, offset, length, options);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeFile(String pathName) {
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeFile(String pathName, Options opts) {
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeFileDescriptor(FileDescriptor fd) {
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);

        return bitmap;
    }

    public void saveImage() {

    }

    //TODO:考虑大图
    public static Bitmap decodeFileDescriptor(FileDescriptor fd,
                                              Rect outPadding, Options opts) {
        Bitmap bitmap = BitmapFactory
                .decodeFileDescriptor(fd, outPadding, opts);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, id);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeResource(Resources res, int id, Options opts) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, id, opts);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeResourceStream(Resources res, TypedValue value,
                                              InputStream is, Rect pad, Options opts) {
        Bitmap bitmap = BitmapFactory.decodeResourceStream(res, value, is, pad,
                opts);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeStream(InputStream is) {
        Bitmap bitmap = BitmapFactory.decodeStream(is);

        return bitmap;
    }

    //TODO:考虑大图
    public static Bitmap decodeStream(InputStream is, Rect outPadding,
                                      Options opts) {
        Bitmap bitmap = BitmapFactory.decodeStream(is, outPadding, opts);

        return bitmap;
    }

    // endregion

    // region Bitmap

    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth,
                                            int dstHeight, boolean filter) {
        Bitmap bitmap = Bitmap.createScaledBitmap(src, dstWidth, dstHeight,
                filter);

        return bitmap;
    }

    public static Bitmap createBitmap(Bitmap src) {
        Bitmap bitmap = Bitmap.createBitmap(src);

        return bitmap;
    }

    public static Bitmap createBitmap(Bitmap source, int x, int y, int width,
                                      int height) {
        Bitmap bitmap = Bitmap.createBitmap(source, x, y, width, height);

        return bitmap;
    }

    public static Bitmap createBitmap(Bitmap source, int x, int y, int width,
                                      int height, Matrix m, boolean filter) {
        Bitmap bitmap = Bitmap.createBitmap(source, x, y, width, height, m,
                filter);
        return bitmap;
    }

    public static Bitmap createBitmap(int width, int height, Config config) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);

        return bitmap;
    }

    public static Bitmap createBitmap(DisplayMetrics display, int width,
                                      int height, Config config) {
        Bitmap bitmap = Bitmap.createBitmap(display, width, height, config);

        return bitmap;
    }

    public static Bitmap createBitmap(int colors[], int offset, int stride,
                                      int width, int height, Config config) {
        Bitmap bitmap = Bitmap.createBitmap(colors, offset, stride, width,
                height, config);

        return bitmap;
    }

    public static Bitmap createBitmap(DisplayMetrics display, int colors[],
                                      int offset, int stride, int width, int height, Config config) {
        Bitmap bitmap = Bitmap.createBitmap(display, colors, offset, stride,
                width, height, config);

        return bitmap;
    }

    public static Bitmap createBitmap(int colors[], int width, int height,
                                      Config config) {
        Bitmap bitmap = Bitmap.createBitmap(colors, width, height, config);

        return bitmap;
    }

    public static Bitmap createBitmap(DisplayMetrics display, int colors[],
                                      int width, int height, Config config) {
        Bitmap bitmap = Bitmap.createBitmap(display, colors, width, height,
                config);

        return bitmap;
    }

    // endregion

    // region MediaStore.Images

    // # MediaStore.Images.Thumbnails

    public static Bitmap getThumbnail(ContentResolver cr, long origId,
                                      int kind, Options options) {
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, origId,
                kind, options);

        return bitmap;
    }

    public static Bitmap getThumbnail(ContentResolver cr, long origId,
                                      long groupId, int kind, Options options) {
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, origId,
                groupId, kind, options);

        return bitmap;
    }

    public static Bitmap getThumbnail(long id, Context context) {
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(), id,
                MediaStore.Images.Thumbnails.MINI_KIND, null);

        return bitmap;
    }

    public static Bitmap getThumbnailBig(long id, Context context) {
        Options options = new Options();
        options.inDither = false;
        options.inPreferredConfig = Config.ARGB_8888;
        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                context.getContentResolver(), id,
                MediaStore.Images.Thumbnails.MINI_KIND, options);

        return bitmap;
    }

    // # MediaStore.Images.Media

    //TODO:考虑大图
    public static Bitmap getBitmap(ContentResolver cr, Uri uri)
            throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);

        return bitmap;
    }

    // 根据原路径获得对应的缩略图
    public static Bitmap getThumbnail(ContentResolver cr, String fileName) {
        Bitmap bitmap = null;
        Options options = new Options();
        options.inDither = false;
        options.inPreferredConfig = Config.ARGB_8888;
        // select condition.
        String whereClause = MediaStore.Images.Media.DATA + " = '" + fileName
                + "'";
        // colection of results.
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, whereClause,
                null, null);
        if (cursor == null || cursor.getCount() == 0) {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
        cursor.moveToFirst();
        // image id in image table.
        String videoId = cursor.getString(cursor
                .getColumnIndex(MediaStore.Images.Media._ID));
        cursor.close();
        if (videoId == null) {
            return null;
        }
        long videoIdLong = Long.parseLong(videoId);
        bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, videoIdLong,
                MediaStore.Images.Thumbnails.MINI_KIND, options);

        return bitmap;
    }

    // # MEDIA_SCANNER_SCAN_FILE

    // 添加到图库，默认拍照是否可以通过MediaStore扫描到
    public static void addGallery(Context context, String path) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    // endregion

    // region ThumbnailUtils

    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, kind);

        return bitmap;
    }

    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(source, width, height);

        return bitmap;
    }

    public static Bitmap extractThumbnail(Bitmap source, int width, int height, int options) {
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(source, width, height, options);
        return bitmap;
    }

    // endregion

    // region ImageLoader


    // endregion

    // region Recycle

    public static Bitmap recycleAndUpdate(Bitmap oldBitmap, Bitmap newBitmap) {

        recycleBitmap(oldBitmap);

        return newBitmap;
    }


    /**
     * 对Bitmap做半透明玻璃玻璃效果
     *
     * @author Huyf Email:my519820363@gmail.com
     */
    public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        LogUtils.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        LogUtils.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }


    public static void recycleBitmap(Bitmap bitmap) {
        logRecycleBitmap(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
        System.gc();
    }

    private static void logRecycleBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
    }

    // endregion

    // region Convert

    // convert Bitmap to ByteArrayOutputStream
    public static ByteArrayOutputStream bitmapToOutputStream(Bitmap image, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);

        if (maxSize > 0) {
            while (baos.toByteArray().length > maxSize) {
                baos.reset();
                options -= 10;
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
        }
        return baos;
    }

    // convert Bitmap to byte array
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    // convert byte array to Bitmap
    public static Bitmap bytesToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapUtils.decodeByteArray(b, 0, b.length);
    }

    // convert byte array to Bitmap
    public static Bitmap bytesToBitmap(byte[] b, int orientation) {

        //解析生成相机返回的图片
        Bitmap bitmap = BitmapUtils.decodeByteArray(b, 0, b.length);

        //调整图片角度
        bitmap = BitmapUtils.rotateBitmap(bitmap, orientation);

        return bitmap;
    }

    // convert Drawable to Bitmap
    public static Bitmap drawableToBitmap(Drawable d) {
        if (d != null && d instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

            return bitmap;
        }
        return null;
    }

    // convert Bitmap to Drawable
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    // convert Drawable to byte array
    public static byte[] drawableToBytes(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    // convert byte array to Drawable
    public static Drawable bytesToDrawable(byte[] b) {
        return bitmapToDrawable(bytesToBitmap(b));
    }

    //endregion

    // region Utils

    public static boolean saveBitmap(Bitmap bitmap, String path) {
        return saveBitmap(bitmap, new File(path));
    }

    public static boolean saveBitmap(Bitmap bitmap, File imageFile) {
        boolean result = false;
        FileOutputStream fos = null;
        try {
            imageFile.createNewFile();
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fos = null;
                imageFile = null;
            }
        }

        return result;
    }

    public static int getSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API 12
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
        }
    }

    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        File currentFile = null;
        String fileName = "";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
        return currentFile.getAbsolutePath();
    }

    public static ImageSize getImageSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final LayoutParams params = imageView.getLayoutParams();

        int width = params.width == LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getWidth(); // Get actual image width
        if (width <= 0) {
            width = params.width; // Get layout width parameter
        }
        if (width <= 0) {
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
        }
        // maxWidth
        // parameter
        if (width <= 0) {
            width = displayMetrics.widthPixels;
        }
        int height = params.height == LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getHeight(); // Get actual image height
        if (height <= 0) {
            height = params.height; // Get layout height parameter
        }
        if (height <= 0) {
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
        }
        // maxHeight
        // parameter
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    public static ImageSize getImageSize(String path) {
        Options bitmapOptions = new Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapUtils.decodeFile(path, bitmapOptions);
        ImageSize size = new ImageSize();
        size.width = bitmapOptions.outWidth;
        size.height = bitmapOptions.outHeight;
        return size;
    }

    public static int getOrientation(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 图片宽高不和width、height一致
    public static Bitmap getScaledBitmap(String path, int width, int height) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = decodeFile(path, options);
        int or = getOrientation(path);
        bitmap = rotateBitmap(bitmap, or);
        return bitmap;
    }

    // 图片宽高不和width、height一致
    public static Bitmap getScaledCompressBitmap(String path, int width, int height, int maxSize) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = decodeFile(path, options);
        int or = getOrientation(path);
        bitmap = rotateBitmap(bitmap, or);
        return bitmap;
    }

    //压缩图片
    public static boolean compressBitmap(String src, String desc) {
        //TODO
        return false;
    }

    //根据新的宽高，生成Bitmap
    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        // 获得图片的宽高
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) width) / w;
        float scaleHeight = ((float) height) / h;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return BitmapUtils.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    //根据新的宽高，生成Bitmap
    public static Bitmap scaleBitmapEqualRatio(Bitmap bitmap, int size, boolean sizeIsMaximum) {
        // 获得图片的宽高
        float w = bitmap.getWidth();
        float h = bitmap.getHeight();

        float p = 1.0f * w / h;

        if ((!sizeIsMaximum && w >= h) || (sizeIsMaximum && w <= h)) {
            h = size;
            w = (int) (h * p);
        } else {
            w = size;
            h = (int) (w / p);
        }

        // 计算缩放比例
        float scaleWidth = bitmap.getWidth() / w;
        float scaleHeight = bitmap.getHeight() / h;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return BitmapUtils.createBitmap(bitmap, 0, 0, (int) w, (int) h, matrix, true);
    }

    //裁剪Bitmap
    public static Bitmap cropBitmap(Bitmap src, int x, int y, int width, int height) {
        if (x == 0 && y == 0 && width == src.getWidth()
                && height == src.getHeight()) {
            return src;
        }
        return BitmapUtils.createBitmap(src, x, y, width, height);
    }

    //按正方形裁切图片
    public static Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;
        return BitmapUtils.createBitmap(bitmap, retX, retY, wh, wh, null, false);

    }

    // 旋转Bitmap
    public static Bitmap rotateBitmap(Bitmap src, int orientation) {
        if (orientation % 360 == 0) {
            return src;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);
        return BitmapUtils.createBitmap(src, 0, 0, w, h, matrix, true);
    }

    // 图片灰掉
    public static Bitmap grayBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(), h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int alpha = 0xFF << 24;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // 获得像素的颜色
                int color = pix[w * i + j];
                int red = ((color & 0x00FF0000) >> 16);
                int green = ((color & 0x0000FF00) >> 8);
                int blue = color & 0x000000FF;
                color = (red + green + blue) / 3;
                color = alpha | (color << 16) | (color << 8) | color;
                pix[w * i + j] = color;
            }
        }
        Bitmap result = BitmapUtils
                .createBitmap(w, h, Config.ARGB_8888);
        result.setPixels(pix, 0, w, 0, 0, w, h);
        return result;
    }

    //添加阴影
    public static Bitmap addShadow(Bitmap originalBitmap, float radius, Context context) {
        /*
         * // 设置光源的方向 float[] direction = new float[]{ 1, 1, 1 }; //设置环境光亮度
         * float light = 0.4f; // 选择要应用的反射等级 float specular = 6; //
         * 向mask应用一定级别的模糊 float blur = 3.5f;
         *
         * EmbossMaskFilter emboss=new
         * EmbossMaskFilter(direction,light,specular,blur);
         */
        // 应用mask

        BlurMaskFilter blurFilter = new BlurMaskFilter(radius,
                BlurMaskFilter.Blur.NORMAL);
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(Color.RED);
        shadowPaint.setAlpha(50);
        shadowPaint.setMaskFilter(blurFilter);
        // shadowPaint.setMaskFilter(emboss);

        int[] offsetXY = new int[2];
        Bitmap shadowBitmap = originalBitmap
                .extractAlpha(shadowPaint, offsetXY);

        Bitmap shadowImage32 = shadowBitmap.copy(Config.ARGB_8888, true);
        if (Build.VERSION.SDK_INT >= 19
                && !shadowImage32.isPremultiplied()) {
            shadowImage32.setPremultiplied(true);
        }
        Canvas c = new Canvas(shadowImage32);
        c.drawBitmap(originalBitmap, offsetXY[0], offsetXY[1], null);

        return shadowImage32;
    }

    // endregion

    // region Help methods

    //反射获得ImageView设置的最大宽度和高度
    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;

                LogUtils.e("TAG", value + "");
            }
        } catch (Exception e) {
        }
        return value;
    }

    // 计算图片的缩放值
    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    // endregion

    // region App Relative

    //根据路劲获取指定大小图片
    public static Bitmap getAdjustedBitmap(String path) {
        Bitmap bitmap = sampleBitmap(path);
        int o = getOrientation(path);
        bitmap = rotateBitmap(bitmap, o);
        return bitmap;
    }

    /****
     * 通过路径获得图片，使用这个方法不用担心OOM
     *
     * @return
     */
    public static Bitmap sampleBitmap(String filePath) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(src, options);
//        int inSampleSize  = calculateInSampleSize(options, MAX_SIZE, MAX_SIZE);
//        options.inJustDecodeBounds = false;
//        options.inSampleSize = inSampleSize;
//
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    //将图片旋转+并获取合适（避免图片大于1MB）的大小
    public static boolean adjustBitmap(String src, String dst) {
        System.gc();
        int or = getOrientation(src);
        Bitmap bitmap = sampleBitmap(src);
        bitmap = rotateBitmap(bitmap, or);
        if (bitmap == null)
            return false;

        if (bitmap == null)
            return false;

        return saveBitmap(bitmap, dst);
    }
    // endregion


    public static boolean checkImageWith(String path) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        //   Bitmap mBitmap =BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        return !(width < 640);

    }

    /**
     * 添加到图库
     */
    public static void galleryAddPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static Drawable decodeLargeResourceImage(Resources resources, int resId) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = resources.getDrawable(resId, null);
        } else {
            try {
                Options opt = new Options();
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                InputStream is = resources.openRawResource(resId);
                drawable = new BitmapDrawable(resources, BitmapFactory.decodeStream(is, null, opt));
            } catch (OutOfMemoryError e) {
                drawable = null;
            }
        }
        return drawable;
    }

    /**
     * 高斯模糊算法
     *
     * @param context
     * @param sentBitmap
     * @param radius
     * @return
     */
    public static Bitmap fastBlur(Context context, Bitmap sentBitmap, int radius) {
//        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        Bitmap bitmap = sentBitmap;
        if (bitmap == null) {
            return null;
        }
        if (radius < 1) {
            return null;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
//        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int temp = 256 * divsum;
        int dv[] = new int[temp];
        for (i = 0; i < temp; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
//        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
//        return (bitmap);
        return Bitmap.createBitmap(pix, 0, w, w, h, Config.ARGB_8888);
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }
}

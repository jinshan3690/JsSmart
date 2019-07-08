package com.js.smart.common.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;


public class ImageUtil {

    public final static int REQUEST_CODE_PHOTO_PICK = 1011;
    public final static int REQUEST_CODE_PHOTO_CAPTURE = 1012;
    public final static int REQUEST_CODE_PHOTO_CAPTURE_WITH_CROP = 1013;
    public final static int REQUEST_CODE_PHOTO_PICK_WITH_CROP = 1014;
    public final static int REQUEST_CODE_PHOTO_CAPTURE_PICK = 1015;
    public static Uri imageUri;

    // 图片保存路径
    public static String imagePath;
    public static String imageCache;

    public static void init(Context context){
        imagePath = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getApplicationInfo().packageName + "/image";
        imageCache = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getApplicationInfo().packageName + "/image/cache";
    }

    /**
     * 选择相册照片
     */
    public static void img_PhotoFromDCIM(AppCompatActivity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, REQUEST_CODE_PHOTO_PICK);
    }

    /**
     * 拍照
     */
    public static void img_PhotoFromCapture(AppCompatActivity context) {
        imageUri = getUri(context);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        context.startActivityForResult(intent, ImageUtil.REQUEST_CODE_PHOTO_CAPTURE);
    }

    /**
     * 选择相册裁剪
     */
    public static void img_cutPhotoFromDCIM(AppCompatActivity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

        intent.setType("image/*");

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        context.startActivityForResult(intent, REQUEST_CODE_PHOTO_PICK);
    }

    /**
     * 剪切照片
     */
    public static void img_cutPhotoFromCapture(AppCompatActivity context, Uri uri, int requestCode) {
        imageUri = Uri.fromFile(ImageUtil.getNewFile(context));
        Intent intent = new Intent("com.android.camera.action.CROP");
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");//指定uri 小米手机不指定会出问题
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // 去面部识别
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 拍照和选择照片
     */
    public static void img_PhotoFromDCIMAndCapture(AppCompatActivity context) {
        imageUri = getUri(context);
        // 相机intent
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //查看图片intent
        Intent DCIMIntent = new Intent(Intent.ACTION_GET_CONTENT);
        DCIMIntent.addCategory(Intent.CATEGORY_OPENABLE);
        DCIMIntent.setType("image/*");
        // intent选择器
        Intent chooserIntent = Intent.createChooser(DCIMIntent, "选择图片");
        // 添加相机intent
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});
        // 发出请求
        context.startActivityForResult(chooserIntent, REQUEST_CODE_PHOTO_CAPTURE_PICK);
    }

    /**
     * 处理返回结果
     *
     * @param context     上下文
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        intent
     * @return bitmap
     * @throws IOException
     */
    public static Bitmap onActivityResult(AppCompatActivity context, int requestCode, int resultCode, Intent data) throws IOException {
        return onActivityResult(context, requestCode, resultCode, data, false, false);
    }

    public static Bitmap onActivityResult(AppCompatActivity context, int requestCode, int resultCode, Intent data, boolean isCutCapture, boolean isCutPhoto) throws IOException {
        Bitmap bitmap = null;
        if (resultCode != AppCompatActivity.RESULT_OK) {
            return null;
        }
        switch (requestCode) {
            case REQUEST_CODE_PHOTO_CAPTURE:// 拍照
                if (isCutCapture) {
                    ImageUtil.img_cutPhotoFromCapture(context, imageUri, REQUEST_CODE_PHOTO_CAPTURE_WITH_CROP);
                } else {
                    if (data != null)
                        bitmap = data.getParcelableExtra("data");
                    else
                        bitmap = getThumbnail(imageUri, 500, context);
                }
                break;
            case REQUEST_CODE_PHOTO_CAPTURE_WITH_CROP:    //照相并裁剪
                bitmap = getThumbnail(imageUri, 500, context);
                break;
            case REQUEST_CODE_PHOTO_PICK:// 相册
                imageUri = data.getData();
                if (isCutPhoto) {
                    ImageUtil.img_cutPhotoFromCapture(context, imageUri, REQUEST_CODE_PHOTO_PICK_WITH_CROP);
                } else {
                    bitmap = getThumbnail(imageUri, 500, context);
                }
                break;
            case REQUEST_CODE_PHOTO_PICK_WITH_CROP: //相册并裁剪
                bitmap = getThumbnail(imageUri, 500, context);
                break;
            case REQUEST_CODE_PHOTO_CAPTURE_PICK: //拍照选择相册
                imageUri = data == null ? imageUri : data.getData();
                bitmap = getThumbnail(imageUri, 500, context);
                break;
        }
        return bitmap;
    }

    /**
     * 压缩图片
     */
    public static Bitmap getThumbnail(Uri uri, int size, AppCompatActivity activity) throws FileNotFoundException, IOException {
        InputStream input = activity.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = activity.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    /**
     * 以省内存方式读取
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

    /**
     * 在相册默认目录DCIM新建一个文件,若无SD卡，保存在缓存目录
     */
    public static File getNewFile(Context context) {
        String picturePath;
        File file = new File(imageCache);
        if (!file.exists()) {
            file.mkdirs();
        }
        String photoName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            picturePath = imageCache + "/" + photoName;
        } else {
            picturePath = context.getCacheDir() + "/" + photoName;
        }

        System.out.println(picturePath);
        return new File(picturePath);
    }

    /**
     * 保存图片到指定路径
     */
    public static String saveImage(String path, Bitmap bitmap) {//生成图片
        String str = null;
        try {
            File src = new File(path);
            if (!src.exists()) {
                src.mkdirs();
            }
            str = path + "/" + System.currentTimeMillis() + ".jpg";
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(str, false));//压缩后图片保存sd
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i("APP", "图片已缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getImageSystemPath() {
        //系统相册目录
        return Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
    }

        /**
         * 保存到系统相册
         */
    public static void saveImageSystem(Context context, Bitmap bitmap) {

        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, String.valueOf(System.currentTimeMillis()) + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bitmap, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath
     */
    public static void cleanImageCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                System.out.println(item.getName());
                item.delete();
            }
            Log.i("TEST", "图片缓存已清除");
        }
    }

    /**
     * 缩放图片
     */
    public static BitmapDrawable scale(Context context, int id, int size) throws IOException {
        InputStream input = context.getResources().openRawResource(id);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = context.getResources().openRawResource(id);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    // 从sd卡上加载图片
    public static BitmapDrawable scale(Context context, String pathName, int size) throws IOException {
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeFile(pathName, onlyBoundsOptions);
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        onlyBoundsOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        onlyBoundsOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, onlyBoundsOptions);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * 获取uri
     */
    private static Uri getUri(Context context) {
        Uri imageUri;
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".fileProvider", ImageUtil.getNewFile(context));
        } else {
            imageUri = Uri.fromFile(ImageUtil.getNewFile(context));
        }
        return imageUri;
    }

    /**
     * 将图片准转为圆形
     */
    public static Bitmap getRoundCornerBitmap(Context context,Bitmap bitmap) {

        int size = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth()
                : bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, size, size);
        final float roundPx = size / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return getRoundBorderBitmap(context, output);
    }

    /**
     * 添加白色边框
     */
    public static Bitmap getRoundBorderBitmap(Context context, Bitmap bitmap) {
        int size = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth()
                : bitmap.getHeight();
        int num = DensityUtil.dp2px(context, 4);
        int sizebig = size + num;
        Bitmap output = Bitmap.createBitmap(sizebig, sizebig, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = Color.parseColor("#FFFFFF");
        final Paint paint = new Paint();
        final float roundPx = sizebig / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawBitmap(bitmap, num / 2, num / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        RadialGradient gradient = new RadialGradient(roundPx, roundPx, roundPx,
                new int[] { Color.WHITE, Color.WHITE,
                        Color.parseColor("#AAAAAAAA") }, new float[] { 0.f,
                0.97f, 1.0f }, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        return output;
    }

    /**
     * 缩放图片
     */
    public static Bitmap scaleBitmap(Context context, int size, int id){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        return scaleBitmap(size, bitmap);
    }

    public static Bitmap scaleBitmap(int size, Bitmap bitmap){

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        int newWidth = size;
        int newHeight = size;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

}

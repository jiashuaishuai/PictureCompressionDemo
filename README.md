# PictureCompressionDemo
图片的尺寸压缩和质量压缩

## 尺寸压缩

压缩图片尺寸压缩后加载内存变小防止oom
关键代码块儿

```java
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;//不将图片解码到内存中
                BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                /**如果设置的值大于1，要求解码器子样的原
                *图像，返回一个较小的图像以保存内存。样本大小是
                *任一维度中对应于单个像素的像素数
                *解码位图中的像素。例如，insamplesize = = 4的回报
                *图像的宽度为原来的1/16，高度为1/4。
                *像素数。任何值< = 1都被视为1。注：本
                *解码器使用基于2的幂的最终值，任何其他值都将
                *舍入到最接近的2的功率。**/
                options.inSampleSize = xxx;
                options.inJustDecodeBounds = false;//将图片解码到内存中
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);

```

## 质量压缩

压缩图片的大小，内存不变，主要用作上传保存本地文件的等

```java
new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                options.inSampleSize = Utils.computeSampleSize(options, 600, displayMetrics.widthPixels * displayMetrics.heightPixels);
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);//首先将图片尺寸压缩，
                int quality = 100;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//将bitmap加载到输出流中
                Log.e("CPP", "  长度1:  " + outputStream.toByteArray().length);
                while (outputStream.size() / 1024 > 1024) {//判断是否大于1
                    outputStream.reset();//清空
                    quality -= 10;//递减10
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//重新加载
                }
                Log.e("CPP", "  长度2:  " + outputStream.size());
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());//获取输入流
                final Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);//转为bitmap
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        test_img.setImageBitmap(bitmap1);//主线程加载
                    }
                });

            }
        }).start();

```

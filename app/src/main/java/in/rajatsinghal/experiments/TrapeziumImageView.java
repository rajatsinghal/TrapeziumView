package in.rajatsinghal.experiments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class TrapeziumImageView extends ImageView {

	public TrapeziumImageView(Context context) {
		super(context);
	}

	public TrapeziumImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TrapeziumImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
		int w = getWidth(), h = getHeight();
		Bitmap roundBitmap = getCroppedBitmap(bitmap, w, h);
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}

	public static Bitmap getCroppedBitmap(Bitmap sbmp, int width, int height) {
		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		//paint.setColor(0xFFFFFF);
		//canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f, radius / 2 + 0.1f, paint);

		int x_distance = (int) (height / Math.tan(Math.toRadians(60)));

		/*Path left_path = new Path();
		left_path.moveTo(0, 0);
		left_path.lineTo(0, height);
		left_path.lineTo(x_distance, height);
		left_path.close();
		canvas.drawPath(left_path, paint);*/

		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(width, 0);
		path.lineTo(width - x_distance, height);
		path.lineTo(x_distance, height);
		path.close();
		canvas.drawPath(path, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}

}
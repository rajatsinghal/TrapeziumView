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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

public class TrapeziumView extends View {

	public int top_color;
	public int bottom_color;
	public int angle;

	public TrapeziumView(Context context) {
		super(context);
	}

	public TrapeziumView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TrapeziumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init()

	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap b = getOrignalBitmap(getWidth(), getHeight());
		Bitmap roundBitmap = getCroppedBitmap(b, getWidth(), getHeight());
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}

	public Bitmap getOrignalBitmap(int width, int height) {
		ShapeDrawable shape1 = new ShapeDrawable(new RectShape());
		shape1.getPaint().setColor(Color.RED);

		ShapeDrawable shape2 = new ShapeDrawable(new RectShape());
		shape2.getPaint().setColor(Color.GREEN);

		LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shape1, shape2});

		layerDrawable.setLayerInset(0, 0, 0, 0, 0);
		layerDrawable.setLayerInset(1, 0, height / 2, 0, 0);

		Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		layerDrawable.setBounds(0, 0, getWidth(), getHeight());
		layerDrawable.draw(new Canvas(b));
		return b;
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
		paint.setColor(Color.parseColor("#FF0000"));

		int x_distance = (int) (height / Math.tan(Math.toRadians(60)));
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
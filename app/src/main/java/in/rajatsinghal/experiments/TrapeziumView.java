package in.rajatsinghal.experiments;

import android.content.Context;
import android.content.res.TypedArray;
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

	public static int default_top_color = Color.parseColor("#FF0000");
	public static int default_bottom_color = Color.parseColor("#00FF00");
	public static int default_slant_angle = 60;

	public int top_color;
	public int bottom_color;
	public int slant_angle;

	public LayerDrawable layer_drawable;

	public TrapeziumView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public TrapeziumView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public TrapeziumView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	void init(Context context, AttributeSet attrs, int defStyle) {
		top_color = default_top_color;
		bottom_color = default_bottom_color;
		slant_angle = default_slant_angle;

		if (attrs != null) {
			TypedArray attributes_values = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrapeziumView, 0, 0);
			top_color = attributes_values.getColor(R.styleable.TrapeziumView_top_color, default_top_color);
			bottom_color = attributes_values.getColor(R.styleable.TrapeziumView_bottom_color, default_bottom_color);
			slant_angle = attributes_values.getInt(R.styleable.TrapeziumView_slant_angle, default_slant_angle);
		}

		ShapeDrawable top_drawable = new ShapeDrawable(new RectShape());
		top_drawable.getPaint().setColor(top_color);

		ShapeDrawable bottom_drawable = new ShapeDrawable(new RectShape());
		bottom_drawable.getPaint().setColor(bottom_color);

		layer_drawable = new LayerDrawable(new Drawable[]{top_drawable, bottom_drawable});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(getCroppedBitmap(getWidth(), getHeight()), 0, 0, null);
	}

	public Bitmap getCroppedBitmap(int width, int height) {
		Bitmap layer_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		layer_drawable.setLayerInset(0, 0, 0, 0, 0);
		layer_drawable.setLayerInset(1, 0, height / 2, 0, 0);
		layer_drawable.setBounds(0, 0, width, height);
		layer_drawable.draw(new Canvas(layer_bitmap));

		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.drawARGB(0, 0, 0, 0);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
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
		Rect rect = new Rect(0, 0, width, height);
		canvas.drawBitmap(layer_bitmap, rect, rect, paint);

		return output;
	}

}
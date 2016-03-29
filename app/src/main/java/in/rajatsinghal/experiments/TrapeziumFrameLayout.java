package in.rajatsinghal.experiments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class TrapeziumFrameLayout extends View {

	Paint mask_paint;
	Path left_path;
	Path right_path;

	public TrapeziumFrameLayout(Context context) {
		super(context);
		init(context, null, 0);
	}

	public TrapeziumFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public TrapeziumFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	void init(Context context, AttributeSet attrs, int defStyle) {
		mask_paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
		mask_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
		setLayerType(LAYER_TYPE_SOFTWARE, mask_paint);

		left_path = new Path();
		right_path = new Path();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight();

		int x_distance = (int) (height / Math.tan(Math.toRadians(60)));

		left_path.moveTo(0, 0);
		left_path.lineTo(0, height);
		left_path.lineTo(x_distance, height);
		left_path.close();
		canvas.drawPath(left_path, mask_paint);

		right_path.moveTo(width, 0);
		right_path.lineTo(width, height);
		right_path.lineTo(width - x_distance, height);
		right_path.close();
		canvas.drawPath(right_path, mask_paint);
	}
}
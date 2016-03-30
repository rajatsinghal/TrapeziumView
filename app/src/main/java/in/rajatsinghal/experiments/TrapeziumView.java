package in.rajatsinghal.experiments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.view.View;

public class TrapeziumView extends View {

	private ShapeDrawable mDrawable;
	Path left_path;
	Path right_path;

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
		/*int x = 10;
		int y = 10;
		int width = 300;
		int height = 50;

		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(0xff74AC23);
		mDrawable.setBounds(x, y, x + width, y + height);*/

		left_path = new Path();
		right_path = new Path();
	}

	protected void onDraw(Canvas canvas) {
		//mDrawable.draw(canvas);

		int width = getWidth();
		int height = getHeight();

		int x_distance = (int) (height / Math.tan(Math.toRadians(60)));

		left_path.moveTo(0, 0);
		left_path.lineTo(0, height);
		left_path.lineTo(x_distance, height);
		left_path.close();
		//canvas.drawPath(left_path, mask_paint);

		mDrawable = new ShapeDrawable(new PathShape(left_path, 1, 1));
		mDrawable.getPaint().setColor(0xff74AC23);
		mDrawable.setBounds(0, 0, width, height);
		mDrawable.draw(canvas);

		/*right_path.moveTo(width, 0);
		right_path.lineTo(width, height);
		right_path.lineTo(width - x_distance, height);
		right_path.close();*/
		//canvas.drawPath(right_path, mask_paint);


	}

}
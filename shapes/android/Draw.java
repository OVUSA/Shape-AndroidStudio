package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.camera2.params.BlackLevelPattern;
import android.icu.text.RelativeDateTimeFormatter;

import java.util.List;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {
  //TODO entirely your job (except onCircle)


	private final Canvas canvas;
	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas;
		this.paint = paint;
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStrokeColor(final StrokeColor c) {
		aint.getColor();
		paint.setColor(c.getColor());
		c.getShape().accept(this);
		if (paint.getColor()==Color.RED){
			paint.setColor(Color.RED);
		}else if (paint.getColor()==Color.BLUE) {
			paint.setColor(Color.BLUE);
		}else if (paint.getColor()==Color.MAGENTA) {
			paint.setColor(Color.MAGENTA);
		}
		paint.getColor();
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		paint.getStyle();

		if(paint.getStyle()== Style.FILL_AND_STROKE) {
			paint.setStyle(Style.FILL_AND_STROKE);
		}else{
			paint.setStyle(Style.STROKE);
		}
		f.getShape().accept(this);
		//paint.getStyle();
		paint.setStyle(Style.FILL);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		for (Shape a: g.getShapes()){
			a.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(),l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(),-l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0, r.getWidth(),r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		paint.setColor(Color.BLUE);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		final float[] pts = new float[(s.getPoints().size()*4)];

		 int i = 0 ;
		 for (Point a :s.getPoints()){
		 pts[i] = a.getX();
		 pts[i+1] = a.getY();
		 pts[i+2] = a.getX();
		 pts[i+3] = a.getY();
	 }

		 canvas.drawLines(pts, paint);
		return null;
	}
}

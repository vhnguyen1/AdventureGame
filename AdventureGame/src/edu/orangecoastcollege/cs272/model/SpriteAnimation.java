package edu.orangecoastcollege.cs272.model;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;

    public SpriteAnimation(
            ImageView imageView, 
            Duration duration, 
            int count,   int columns,
            int offsetX, int offsetY,
            int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }
    

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columns;
		result = prime * result + count;
		result = prime * result + height;
		result = prime * result + ((imageView == null) ? 0 : imageView.hashCode());
		result = prime * result + lastIndex;
		result = prime * result + offsetX;
		result = prime * result + offsetY;
		result = prime * result + width;
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpriteAnimation other = (SpriteAnimation) obj;
		if (columns != other.columns)
			return false;
		if (count != other.count)
			return false;
		if (height != other.height)
			return false;
		if (imageView == null) {
			if (other.imageView != null)
				return false;
		} else if (!imageView.equals(other.imageView))
			return false;
		if (lastIndex != other.lastIndex)
			return false;
		if (offsetX != other.offsetX)
			return false;
		if (offsetY != other.offsetY)
			return false;
		if (width != other.width)
			return false;
		return true;
	}


	protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}
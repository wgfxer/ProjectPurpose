package com.wgfxer.projectpurpose.models;

import com.wgfxer.projectpurpose.R;

/**
 * Модель для темы цели
 */
public class PurposeTheme {

    public static final int[] GRADIENTS = new int[]{
            R.drawable.gradient1,
            R.drawable.gradient2,
            R.drawable.gradient3,
            R.drawable.gradient4,
            R.drawable.gradient5,
            R.drawable.gradient6,
            R.drawable.gradient7,
            R.drawable.gradient8,
            R.drawable.gradient9,
            R.drawable.gradient10,
            R.drawable.gradient11,
            R.drawable.gradient12,
            R.drawable.gradient13,
            R.drawable.gradient14,
            R.drawable.gradient15,
            R.drawable.gradient16,
            R.drawable.gradient17,
            R.drawable.gradient18,
            R.drawable.gradient19,
            R.drawable.gradient20

    };

    private String imagePath;
    private int gradientPosition = (int) (Math.random() * GRADIENTS.length);
    private float gradientAlpha = 0.8f;
    private boolean isWhiteFont = true;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getGradientPosition() {
        return gradientPosition;
    }

    public void setGradientPosition(int gradientPosition) {
        this.gradientPosition = gradientPosition;
    }

    public float getGradientAlpha() {
        return gradientAlpha;
    }

    public void setGradientAlpha(float gradientAlpha) {
        this.gradientAlpha = gradientAlpha;
    }

    public boolean isWhiteFont() {
        return isWhiteFont;
    }

    public void setWhiteFont(boolean whiteFont) {
        isWhiteFont = whiteFont;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurposeTheme that = (PurposeTheme) o;

        if (gradientPosition != that.gradientPosition) return false;
        if (Float.compare(that.gradientAlpha, gradientAlpha) != 0) return false;
        if (isWhiteFont != that.isWhiteFont) return false;
        return imagePath != null ? imagePath.equals(that.imagePath) : that.imagePath == null;
    }

    @Override
    public int hashCode() {
        int result = imagePath != null ? imagePath.hashCode() : 0;
        result = 31 * result + gradientPosition;
        result = 31 * result + (gradientAlpha != +0.0f ? Float.floatToIntBits(gradientAlpha) : 0);
        result = 31 * result + (isWhiteFont ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurposeTheme{" +
                "imagePath='" + imagePath + '\'' +
                ", gradientPosition=" + gradientPosition +
                ", gradientAlpha=" + gradientAlpha +
                ", isWhiteFont=" + isWhiteFont +
                '}';
    }
}

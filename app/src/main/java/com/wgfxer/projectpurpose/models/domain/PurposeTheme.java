package com.wgfxer.projectpurpose.models.domain;

import com.wgfxer.projectpurpose.R;

import androidx.annotation.NonNull;

public class PurposeTheme {
    private String imagePath;
    private int gradientId;
    private float gradientAlpha;
    private boolean isWhiteFont;

    public PurposeTheme() {
        int[] gradients = new int[]{
                R.drawable.gradient1,  //исправить, если добавим новый градиент,то постоянно придется пополнять список
                R.drawable.gradient2,                       //плюс можно забыть
                R.drawable.gradient3,
                R.drawable.gradient4,
                R.drawable.gradient5,
                R.drawable.gradient6,
                R.drawable.gradient7,
                R.drawable.gradient8,
                R.drawable.gradient9,
                R.drawable.gradient10,
                R.drawable.gradient11,
                R.drawable.gradient12};
        int randomGradientIndex = (int) (Math.random() * gradients.length);
        gradientId = gradients[randomGradientIndex];
        gradientAlpha = 0.8f;
        isWhiteFont = true;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getGradientId() {
        return gradientId;
    }

    public void setGradientId(int gradientId) {
        this.gradientId = gradientId;
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

        if (gradientId != that.gradientId) return false;
        if (Float.compare(that.gradientAlpha, gradientAlpha) != 0) return false;
        if (isWhiteFont != that.isWhiteFont) return false;
        return imagePath != null ? imagePath.equals(that.imagePath) : that.imagePath == null;
    }

    @Override
    public int hashCode() {
        int result = imagePath != null ? imagePath.hashCode() : 0;
        result = 31 * result + gradientId;
        result = 31 * result + (gradientAlpha != +0.0f ? Float.floatToIntBits(gradientAlpha) : 0);
        result = 31 * result + (isWhiteFont ? 1 : 0);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "PurposeTheme{" +
                "imagePath='" + imagePath + '\'' +
                ", gradientId=" + gradientId +
                ", gradientAlpha=" + gradientAlpha +
                ", isWhiteFont=" + isWhiteFont +
                '}';
    }
}

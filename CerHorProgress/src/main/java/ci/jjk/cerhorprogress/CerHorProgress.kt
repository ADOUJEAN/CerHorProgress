/*
 * Copyright (C) 2021 adoujean1996@gmail.com
 *
 */
package ci.jjk.cerhorprogress

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

/**
 * CerHorProgress is a subclass of {@link android.view.View} class
 * */
class CerHorProgress (context: Context, attrs: AttributeSet) : View(context, attrs) {
    /**
     * Progress types
     * CIRCULAR : Default is circular. Set @param progressType="cercle" for use circular progress
     * HORIZONTAL : Set @param progressType="HORIZONTAL" for use Horizontal progress
     */
    private var progressType = "cercle"
    private var useAdjust = true

    private var strokeWidth = 4f
    private var progress = 0f
    private var progressCercleText = ""
    private var progressCercleTextUnity = ""
    private var progressMin = 0
    private var progressMax = 100

    /** Progress trace background color **/
    private var bgColor = Color.DKGRAY

    /** Progress trace forground color **/
    private var fgColor = Color.BLUE

    private var rectF: RectF? = null
    private var backgroundPaint: Paint? = null
    private var foregroundPaint: Paint? = null

    /** Start the Circular progress at 6 o'clock */
    private val startAngle = 90


    /** Setters and Getters of preview variables **/
    fun getStrokeWidth(): Float {
        return strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        backgroundPaint!!.strokeWidth = strokeWidth
        foregroundPaint!!.strokeWidth = strokeWidth
        invalidate()
        requestLayout() //Because it should recalculate its bounds
    }

    fun getProgressCercleTextUnity(): String {
        return progressCercleTextUnity
    }

    fun setProgressCercleTextUnity(progressTextUnity: String) {
        this.progressCercleTextUnity = progressTextUnity
        invalidate()
    }

    fun getProgressCercleText(): String {
        return progressCercleText
    }

    fun setProgressCercleText(progressCercleText: String) {
        this.progressCercleText = progressCercleText
        invalidate()
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun getProgressMin(): Int {
        return progressMin
    }

    fun setProgressMin(min: Int) {
        this.progressMin = min
        invalidate()
    }

    fun getProgressMax(): Int {
        return progressMax
    }

    fun setProgressMax(progressMax: Int) {
        this.progressMax = progressMax
        invalidate()
    }

    fun getForgroundColor(): Int {
        return fgColor
    }

    fun setForgroundColor(color: Int) {
        this.fgColor = color
        foregroundPaint!!.color = color
        invalidate()
        requestLayout()
    }

    fun getBackGroundColor(): Int {
        return bgColor
    }

    fun setBackGroundColor(color: Int) {
        this.bgColor = color
        backgroundPaint!!.color = color
        invalidate()
        requestLayout()
    }

    private fun init(context: Context, attrs: AttributeSet) {

        rectF = RectF()
        /** Obtain values from the XML layout **/

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CerHorProgress,
            0, 0
        )
        /** Reading values from the XML layout **/
        try {
            strokeWidth = typedArray.getDimension(
                R.styleable.CerHorProgress_progressThickness,
                strokeWidth
            )
            progressMin = typedArray.getInt(R.styleable.CerHorProgress_progressMin, progressMin)
            progressMax = typedArray.getInt(R.styleable.CerHorProgress_progressMax, progressMax)
            progress = typedArray.getFloat(R.styleable.CerHorProgress_progress, progress)
            bgColor = typedArray.getInt(R.styleable.CerHorProgress_progressBackgroundColor, bgColor)
            fgColor = typedArray.getInt(R.styleable.CerHorProgress_progressForgroundColor, fgColor)
            useAdjust = typedArray.getBoolean(R.styleable.CerHorProgress_useAdjustColor, useAdjust)

            if (typedArray.getString(R.styleable.CerHorProgress_progressCercleText)!=null) {
                progressCercleText =
                    typedArray.getString(R.styleable.CerHorProgress_progressCercleText).toString()
            }
            if (typedArray.getString(R.styleable.CerHorProgress_progressCercleTextUnity)!=null) {
                progressCercleTextUnity =
                    typedArray.getString(R.styleable.CerHorProgress_progressCercleTextUnity).toString()
            }

            if (typedArray.getString(R.styleable.CerHorProgress_progressType)!=null) {
                progressType =
                    typedArray.getString(R.styleable.CerHorProgress_progressType).toString()
            }

        } finally {
            typedArray.recycle()
        }

        /** Initialise progress paint **/
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint!!.color = if(useAdjust) adjustAlpha(fgColor, 0.3f) else bgColor
        backgroundPaint!!.style = Paint.Style.STROKE
        backgroundPaint!!.strokeWidth = strokeWidth
        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        foregroundPaint!!.color = fgColor
        foregroundPaint!!.style = Paint.Style.STROKE
        foregroundPaint!!.strokeWidth = strokeWidth

    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /** get canvas height and width **/

        val hCanvas = height.toFloat()
        val wCanvas = width.toFloat()

if (progressType=="cercle") {

    /** Draw cercle and set paint **/
    canvas.drawOval(rectF!!, backgroundPaint!!)
    val angle = 360 * progress / progressMax
    canvas.drawArc(rectF!!, startAngle.toFloat(), angle, false, foregroundPaint!!)

    /** Define Text height and width **/
    val h = hCanvas/2
    val w = wCanvas/2

    /** Set the size on the two Texts according to cercle height **/
    val textSize = h / 3

    /** Set cercle Text paint **/

    val textPaint = TextPaint()
    textPaint.setTextSize(textSize)
    textPaint.color = Color.BLACK
    textPaint.setTextAlign(Paint.Align.CENTER)

    val textPaintUnity = TextPaint()
    textPaintUnity.setTextSize(textSize)
    textPaintUnity.color = Color.GRAY
    textPaintUnity.setTextAlign(Paint.Align.CENTER)

    /** Draw cercle text **/
    canvas.drawText(progressCercleText, w, h - ((textPaint.descent() - textPaint.ascent()) / 2), textPaint)
    canvas.drawText(progressCercleTextUnity, w, h + textPaintUnity.descent() - textPaintUnity.ascent(), textPaintUnity)
}else{
    /** Draw line and set paint for Horizontal progress **/
    canvas.drawLine(
            0f, // startX
            (height / 2).toFloat(), // startY
            wCanvas, // stopX
             hCanvas/2, // stopY
            backgroundPaint!! // Paint
    )

    /** Define stop indicator **/
    val stopIndicator = wCanvas * progress / progressMax
    canvas.drawLine(
            0f, // startX
            (hCanvas / 2), // startY
            stopIndicator, // stopX
            (hCanvas / 2), // stopY
            foregroundPaint!! // Paint
 )
}
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)

        if (progressType=="cercle") {
            setMeasuredDimension(min, min)
        }else{
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        }
        rectF!![0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2] =
            min - strokeWidth / 2
    }


    /**
     * Set the progress with an animation.
     * Note that the [ObjectAnimator] Class automatically set the progress
     * so don't call the [com.mrn.customprogressbar.CircleProgressBar.setProgress] directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    fun setProgressWithAnimation(progress: Float) {
        animation(progress)
    }

    fun setProgressWithAnimationAndMax(progress: Float, animMaxProgress: Float) {

        val handler = Handler()
        animation(animMaxProgress)
        handler.postDelayed(
            Runnable {animation(progress)
                setProgressCercleText("${progress.toInt()}")},
            1500
        )
    }

    private fun animation(progress: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = 1000
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    init {
        init(context, attrs)
    }
}
//package com.estimote.indoorapp
//
//import android.animation.ValueAnimator
//import android.content.Context
//import android.graphics.*
//import android.support.annotation.Keep
//import android.util.AttributeSet
//import android.view.View
//import android.view.animation.LinearInterpolator
//
//import com.estimote.indoorsdk_module.R
//import com.estimote.indoorsdk_module.algorithm.model.BeaconWithDistance
//import com.estimote.indoorsdk_module.cloud.*
//import com.estimote.indoorsdk_module.algorithm.IndoorLocationManager
//import com.estimote.indoorsdk_module.common.extensions.*
//import com.estimote.indoorsdk_module.view.animation.PositionAnimator
//
//
///**
// * Main view for drawing location (walls, beacons, objects, etc.) and user position on it.
// * You need to declare it in your XML layout file first and then traditionally bind it to an object.
// *
// * @author Pawel Dylag (pawel.dylag@estimote.com)
// */
//@Keep
//class IndoorLocationView : View {
//
//    internal class ViewLocationPosition(val x: Double = 0.0,
//                                        val y: Double = 0.0,
//                                        val orientation: Double = 0.0,
//                                        val isHidden: Boolean = false)
//
//    // STYLEABLE
//    private var mWallPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private var mCustomPoint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private var mNearbyBeaconPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private var mNearbyBeaconTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//    private var mBeaconBitmapScale = 0.3f
//    private var mWallStrokeWidth = 5f
//    private var mCustomPointStrokeWidth = 20f
//    private var mNearbyBeaconRadius = 40f
//    private var mLocationPadding = 10
//    private var mPositionAnimationDuration = 800L
//
//    // DATA OBJECTS
//    private var mLocation = Location()
//    private var mPosition = ViewLocationPosition(isHidden = true)
//    private var mNearbyBeacons: List<BeaconWithDistance> = emptyList()
//    private var mCustomPoints: List<LocationPosition> = emptyList()
//
//    // SCREEN OBJECTS
//    private val mLocationPath = Path()
//    private val mNearbyBeaconsPath = Path()
//    private val mBeaconColorToBitmapMapping = createColorToBitmapMapping()
//    private val mPositionBitmap = createPositionBitmap()
//
//    // CURRENT DRAW PARAMETERS
//    private val mDrawParams = DrawParams()
//
//    // OBJECTS FOR TRANSLATIONS
//    private val mMatrix: Matrix = Matrix()
//
//    private val mPositionAnimator = PositionAnimator(mPositionAnimationDuration, LinearInterpolator(), ValueAnimator())
//
//    init {
//        // walls
//        mWallPaint.style = Paint.Style.STROKE
//        mWallPaint.color = context.resources.getColor(R.color.colorPrimary)
//        mWallPaint.strokeWidth = mWallStrokeWidth
//        // beacons
//        mCustomPoint.style = Paint.Style.FILL
//        mCustomPoint.color = context.resources.getColor(R.color.colorAccent)
//        mCustomPoint.strokeWidth = mCustomPointStrokeWidth
//        mNearbyBeaconPaint.style = Paint.Style.FILL
//        mNearbyBeaconPaint.color = context.resources.getColor(R.color.colorNearbyBeaconCircle)
//        mNearbyBeaconPaint.strokeWidth = 5f
//        // debug text
//        mNearbyBeaconTextPaint.color = context.resources.getColor(R.color.colorText)
//        mNearbyBeaconTextPaint.textAlign = Paint.Align.CENTER
//        mNearbyBeaconTextPaint.textSize = 32f
//
//    }
//
//    constructor(context: Context) : super(context) {}
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        updateDrawParams()
//        drawLocation(canvas)
//        drawBeacons(canvas)
//        drawCustomPoints(canvas)
//        drawNearbyBeacons(canvas)
//        drawPosition(canvas)
//    }
//
//    //
//
//    private fun createPositionBitmap(): Bitmap {
//        return BitmapFactory.decodeResource(context.resources, EstimoteDrawableFactory.getPositionDrawable())
//    }
//
//    private fun createColorToBitmapMapping(): Map<LocationBeaconColor, Bitmap> {
//        return LocationBeaconColor.values().associate { it.to(createBeaconColorBitmap(it)) }
//    }
//
//    private fun createBeaconColorBitmap(color: LocationBeaconColor): Bitmap {
//        return BitmapFactory.decodeResource(context.resources, EstimoteDrawableFactory.getBeaconDrawable(color))
//    }
//
//    private fun createBeaconUnknownColorBitmap(): Bitmap {
//        return BitmapFactory.decodeResource(context.resources, EstimoteDrawableFactory.getUnknownColorBeaconDrawable())
//    }
//
//    private fun drawLocation(canvas: Canvas) {
//        mLocationPath.reset()
//        mLocation.walls.forEach {
//            mLocationPath.moveTo(realXtoViewX(it.x1), realYtoViewY(it.y1))
//            mLocationPath.lineTo(realXtoViewX(it.x2), realYtoViewY(it.y2))
//        }
//        canvas.drawPath(mLocationPath, mWallPaint)
//    }
//
//    private fun drawBeacons(canvas: Canvas) {
//        mLocation.beacons.forEach { (beacon, position) ->
//            mMatrix.reset()
//            val beaconBitmap = mBeaconColorToBitmapMapping[beacon.color] ?: createBeaconUnknownColorBitmap()
//            val bitmapWidth = beaconBitmap.width
//            val bitmapHeight = beaconBitmap.height
//            val x = realXtoViewX(position.x)
//            val y = realYtoViewY(position.y)
//            mMatrix.setTranslate(x - bitmapWidth / 2f, y - bitmapHeight / 2f)
//            mMatrix.postScale(mBeaconBitmapScale, mBeaconBitmapScale, x, y)
//            mMatrix.postRotate(position.orientation.toFloat(), x, y)
//            canvas.drawBitmap(beaconBitmap, mMatrix, null)
//        }
//    }
//
//    //
//    // PRIVATE
//    private fun drawNearbyBeacons(canvas: Canvas) {
//        mNearbyBeaconsPath.reset()
//        mNearbyBeacons.forEach {
//            mNearbyBeaconsPath.addCircle(realXtoViewX(it.beacon.position.x), realYtoViewY(it.beacon.position.y), mNearbyBeaconRadius, Path.Direction.CW)
//        }
//        canvas.drawPath(mNearbyBeaconsPath, mNearbyBeaconPaint)
//        mNearbyBeacons.forEach {
//            canvas.drawText(it.distance.toInt().toString(), realXtoViewX(it.beacon.position.x), realYtoViewY(it.beacon.position.y), mNearbyBeaconTextPaint)
//        }
//    }
//
//
//    private fun drawPosition(canvas: Canvas) {
//        if (!mPosition.isHidden) {
//            mMatrix.reset()
//            val bitmapWidth = mPositionBitmap.width.toFloat()
//            val bitmapHeight = mPositionBitmap.height.toFloat()
//            val x = realXtoViewX(mPosition.x)
//            val y = realYtoViewY(mPosition.y)
//            mMatrix.setTranslate(x - bitmapWidth / 2f, y - bitmapHeight / 2f)
//            mMatrix.postRotate(mPosition.orientation.toFloat(), x, y)
//            canvas.drawBitmap(mPositionBitmap, mMatrix, null)
//        }
//    }
//
//    private fun drawCustomPoints(canvas: Canvas) {
//        mCustomPoints.forEach {
//            canvas.drawPoint(realXtoViewX(it.x), realYtoViewY(it.y), mCustomPoint)
//        }
//    }
//
//    private fun updateDrawParams() {
//        mDrawParams.viewWidth = width - (paddingLeft + paddingRight)
//        mDrawParams.viewHeight = height - (paddingTop + paddingBottom)
//        mDrawParams.centerX = paddingLeft + mDrawParams.viewWidth / 2.0
//        mDrawParams.centerY = paddingTop + mDrawParams.viewHeight / 2.0
//        mDrawParams.scale = findScaleForViewOrientation(mLocationPadding)
//    }
//
//    private fun findScaleForViewOrientation(thresholdPercentage: Int): Double {
//        val thresholdX = mDrawParams.viewWidth * thresholdPercentage / 100
//        val scaleX = (mDrawParams.viewWidth - thresholdX) / mDrawParams.locationWidth
//        val thresholdY = mDrawParams.viewHeight * thresholdPercentage / 100
//        val scaleY = (mDrawParams.viewHeight - thresholdY) / mDrawParams.locationHeight
//        return Math.min(scaleX, scaleY)
//    }
//
//    private fun realXtoViewX(x: Double): Float {
//        val vectorToCenterX = mDrawParams.centerX - mDrawParams.locationWidth / 2 * mDrawParams.scale
//        return (x * mDrawParams.scale + vectorToCenterX).toFloat()
//    }
//
//    private fun realYtoViewY(y: Double): Float {
//        val vectorToCenterY = mDrawParams.centerY - mDrawParams.locationHeight / 2 * mDrawParams.scale
//        return (mDrawParams.viewHeight - (y * mDrawParams.scale + vectorToCenterY)).toFloat()
//    }
//
//    private fun LocationPosition.convertToViewPosition(): ViewLocationPosition {
//        return ViewLocationPosition(this.x, this.y, this.orientation)
//    }
//
//    //
//    // PUBLIC
//    //
//
//    /**
//     * Set the Location object for this view.
//     * You need to provide the view with location to draw.
//     * You can acquire [Location] objects from [IndoorCloudManager]
//     */
//    fun setLocation(location: Location) {
//        mLocation = location.translateAllCoordinatesToPositiveValues()
//        mDrawParams.locationWidth = Math.abs(location.getMaxX() - location.getMinX())
//        mDrawParams.locationHeight = Math.abs(location.getMaxY() - location.getMinY())
//        invalidate()
//    }
//
//    /**
//     * Call this method when you get the position from [IndoorLocationManager].
//     * This will update user's icon position on this view.
//     */
//    fun updatePosition(newPosition: LocationPosition) {
//        postOnAnimation {
//            if (mPosition.isHidden) {
//                redrawNewPosition(newPosition)
//            } else {
//                mPositionAnimator.animate(mPosition, newPosition.convertToViewPosition(), { redrawNewPosition(it) })
//            }
//        }
//    }
//
//
//    private fun redrawNewPosition(position: LocationPosition) {
//        mPosition = position.convertToViewPosition()
//        invalidate()
//    }
//
//    /**
//     * Draws set of custom points on view.
//     * This method is used rather for debugging, but you can draw your custom points with it.
//     */
//    fun setCustomPoints(points: List<LocationPosition>) {
//        mCustomPoints = points
//        postInvalidate()
//    }
//
//
//    /**
//     * Sets the color of location's walls.
//     */
//    fun setBorderPaint(paint: Paint) {
//        this.mWallPaint = paint
//    }
//
//    /**
//     * Hides the position (user avatar) from view until the next [updatePosition] is called.
//     */
//    fun hidePosition() {
//        mPosition = ViewLocationPosition(isHidden = true)
//    }
//
//}
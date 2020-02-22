package com.example.visitus.utils

import android.graphics.Bitmap
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.example.visitus.R
import com.example.visitus.utils.Constants.EMAIL_STRING_PATTERN
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun ImageView.setImageWithGlide(bitmap: Bitmap){
    Glide.with(context)
        .load(bitmap)
        .centerCrop()
        .placeholder(R.drawable.avatar_background)
        .into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun getTextFromInputLayout(layout: TextInputLayout) = layout.editText?.text.toString()

fun getTimestampFromCalendar(calendar: Calendar) = calendar.timeInMillis

fun getCalendarFromTimeStamp(timestamp: Long) : Calendar{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return calendar
}

fun ImageView.downloadImage(url: String?){
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.avatar)
        .into(this)
}

fun TextInputLayout.isEmpty() : Boolean {
    if(this.editText?.text.toString() == "" || this.editText == null){
        return true
    }
    return false
}

fun Bitmap.getResizedBitmap(maxSize: Int): Bitmap? {
    var width = this.width
    var height = this.height
    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(this, width, height, true)
}

fun isValidMail(email: String): Boolean {
    return Pattern.compile(EMAIL_STRING_PATTERN).matcher(email).matches()
}
fun isValidMobile(phone: String): Boolean {
    return Patterns.PHONE.matcher(phone).matches()
}


/**
 * Pattern: yyyy-MM-dd HH:mm:ss
 */
fun Date.formatToServerDateTimeDefaults(): String{
    val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToTruncatedDateTime(): String{
    val sdf= SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: yyyy-MM-dd
 */
fun Date.formatToServerDateDefaults(): String{
    val sdf= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToServerTimeDefaults(): String{
    val sdf= SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy HH:mm:ss
 */
fun Date.formatToViewDateTimeDefaults(): String{
    val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy
 */
fun Date.formatToViewDateDefaults(): String{
    val sdf= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToViewTimeDefaults(): String{
    val sdf= SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date{
    val cal = Calendar.getInstance()
    cal.time=this
    cal.add(field, amount)

    this.time = cal.time.time

    cal.clear()

    return this
}

fun Date.addYears(years: Int): Date{
    return add(Calendar.YEAR, years)
}
fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}
fun Date.addDays(days: Int): Date{
    return add(Calendar.DAY_OF_MONTH, days)
}
fun Date.addHours(hours: Int): Date{
    return add(Calendar.HOUR_OF_DAY, hours)
}
fun Date.addMinutes(minutes: Int): Date{
    return add(Calendar.MINUTE, minutes)
}
fun Date.addSeconds(seconds: Int): Date{
    return add(Calendar.SECOND, seconds)
}
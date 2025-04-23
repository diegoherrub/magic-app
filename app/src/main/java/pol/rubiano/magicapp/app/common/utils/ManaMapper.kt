package pol.rubiano.magicapp.app.common.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.TypedValue
import androidx.core.content.ContextCompat
import pol.rubiano.magicapp.R

fun mapManaSymbols(context: Context, text: String): SpannableString {
    val spannable = SpannableString(text)
    val pattern = "\\{([^}]+)\\}".toRegex()
    val desiredSizeDp = 16f
    val desiredSizePx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, desiredSizeDp, context.resources.displayMetrics
    ).toInt()

    val mapping = mapOf(
        "T" to R.drawable.mana_t,
        "Q" to R.drawable.mana_q,
        "E" to R.drawable.mana_e,
        "P" to R.drawable.mana_p,
        "PW" to R.drawable.mana_pw,
        "CHAOS" to R.drawable.mana_chaos,
        "A" to R.drawable.mana_a,
        "TK" to R.drawable.mana_tk,
        "X" to R.drawable.mana_x,
        "Y" to R.drawable.mana_y,
        "Z" to R.drawable.mana_z,
        "0" to R.drawable.mana_0,
        "1" to R.drawable.mana_1,
        "2" to R.drawable.mana_2,
        "3" to R.drawable.mana_3,
        "4" to R.drawable.mana_4,
        "5" to R.drawable.mana_5,
        "6" to R.drawable.mana_6,
        "7" to R.drawable.mana_7,
        "8" to R.drawable.mana_8,
        "9" to R.drawable.mana_9,
        "10" to R.drawable.mana_10,
        "11" to R.drawable.mana_11,
        "12" to R.drawable.mana_12,
        "13" to R.drawable.mana_13,
        "14" to R.drawable.mana_14,
        "15" to R.drawable.mana_15,
        "16" to R.drawable.mana_16,
        "17" to R.drawable.mana_17,
        "18" to R.drawable.mana_18,
        "19" to R.drawable.mana_19,
        "20" to R.drawable.mana_20,
        "100" to R.drawable.mana_100,
        "1000000" to R.drawable.mana_1000000,
        "∞" to R.drawable.mana_infinity,
        "W" to R.drawable.mana_w,
        "U" to R.drawable.mana_u,
        "B" to R.drawable.mana_b,
        "R" to R.drawable.mana_r,
        "G" to R.drawable.mana_g,
        "C" to R.drawable.mana_c,
        "W/U" to R.drawable.mana_wu,
        "W/B" to R.drawable.mana_wb,
        "U/B" to R.drawable.mana_ub,
        "U/R" to R.drawable.mana_ur,
        "B/R" to R.drawable.mana_br,
        "B/G" to R.drawable.mana_bg,
        "R/G" to R.drawable.mana_rg,
        "R/W" to R.drawable.mana_rw,
        "G/W" to R.drawable.mana_gw,
        "G/U" to R.drawable.mana_gu,
        "2/W" to R.drawable.mana_2w,
        "2/U" to R.drawable.mana_2u,
        "2/B" to R.drawable.mana_2b,
        "2/R" to R.drawable.mana_2r,
        "2/G" to R.drawable.mana_2g,
        "W/P" to R.drawable.mana_wp,
        "U/P" to R.drawable.mana_up,
        "B/P" to R.drawable.mana_bp,
        "R/P" to R.drawable.mana_rp,
        "G/P" to R.drawable.mana_gp,
        "S" to R.drawable.mana_s,
        "B/G/P" to R.drawable.mana_bgp,
        "B/R/P" to R.drawable.mana_brp,
        "G/U/P" to R.drawable.mana_gup,
        "G/W/P" to R.drawable.mana_gwp,
        "R/G/P" to R.drawable.mana_rgp,
        "R/W/P" to R.drawable.mana_rwp,
        "U/B/P" to R.drawable.mana_ubp,
        "U/R/P" to R.drawable.mana_urp,
        "W/B/P" to R.drawable.mana_wbp,
        "W/U/P" to R.drawable.mana_wup,
        "C/W" to R.drawable.mana_cw,
        "C/U" to R.drawable.mana_cu,
        "C/B" to R.drawable.mana_cb,
        "C/R" to R.drawable.mana_cr,
        "C/G" to R.drawable.mana_cg,
        "H" to R.drawable.mana_h,
        "C/P" to R.drawable.mana_cp,
        "HW" to R.drawable.mana_hw,
        "HR" to R.drawable.mana_hr,
        "L" to R.drawable.mana_l,
    )

    pattern.findAll(text).forEach { match ->
        val symbol = match.groupValues[1]
        val drawableRes = mapping[symbol]
        if (drawableRes != null) {
            val drawable = ContextCompat.getDrawable(context, drawableRes)
            drawable?.setBounds(0, 0, desiredSizePx, desiredSizePx)
            if (drawable != null) {
                val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM)
                spannable.setSpan(
                    imageSpan,
                    match.range.first,
                    match.range.last + 1,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    return spannable
}
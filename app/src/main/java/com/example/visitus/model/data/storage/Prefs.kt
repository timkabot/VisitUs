package com.example.visitus.model.data.storage

import com.example.visitus.entity.Profile
import com.marcinmoskala.kotlinpreferences.PreferenceHolder


/**
 * @author Konstantin Tskhovrebov (aka terrakok). Date: 28.03.17
 */

object Prefs: PreferenceHolder() {
    var token: String by bindToPreferenceField("")
    var user: Profile? by bindToPreferenceFieldNullable(null)
}

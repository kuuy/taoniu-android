package com.kuuy.taoniu.ui.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData

class NumberEditText : AppCompatEditText {

  var phoneNumber = MutableLiveData("")
  var onNumberChangeListener: ((String) -> Unit)? = null

  constructor(
    context: Context,
    attributeSet: AttributeSet?
  ) : super(context, attributeSet) {

    addTextChangedListener(
      onTextChanged = { mText, _, before, count ->
        if (before < count) {
          if (mText?.length == 3 || mText?.length == 8) {
            text?.append(" ")
          }
        }
      },
      afterTextChanged = {
        it?.let {
          val number = it.toString().replace(" ", "")
          phoneNumber.postValue(number)
          onNumberChangeListener?.let { callback ->
            callback(number)
          }
        }
      }
    )
  }

  fun clear() {
    text?.clear()
    phoneNumber.postValue("")
    onNumberChangeListener?.let {
      it("")
    }
  }
}


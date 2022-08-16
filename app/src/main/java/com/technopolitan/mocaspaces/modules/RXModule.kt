package com.technopolitan.mocaspaces.modules

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject


@Module
class RXModule {

    fun getTextWatcherObservable(editText: EditText): Observable<String> {
        val subject: PublishSubject<String> = PublishSubject.create()
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                subject.onNext(s.toString())
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        return subject
    }
}
package org.foodshare_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffresViewModel : ViewModel() {
    private val _offres = MutableLiveData<List<OffreSimple>>()
    val offres: LiveData<List<OffreSimple>> = _offres

    private val _erreur = MutableLiveData<String>()
    val erreur: LiveData<String> = _erreur

    fun chargerOffres() {
        RetrofitInstance.api.getOffres().enqueue(object : Callback<List<OffreSimple>> {
            override fun onResponse(call: Call<List<OffreSimple>>, response: Response<List<OffreSimple>>) {
                if (response.isSuccessful) {
                    _offres.value = response.body() ?: emptyList()
                } else {
                    _erreur.value = "Erreur HTTP : ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<OffreSimple>>, t: Throwable) {
                _erreur.value = "Échec réseau : ${t.message}"
            }
        })
    }
}
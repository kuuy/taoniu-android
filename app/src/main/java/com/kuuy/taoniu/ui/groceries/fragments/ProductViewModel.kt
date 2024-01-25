package com.kuuy.taoniu.ui.groceries.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.groceries.dto.ProductInfoDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.BarcodeInfoDto
import com.kuuy.taoniu.data.groceries.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.kuuy.taoniu.ui.base.BaseViewModel

@HiltViewModel
class ProductViewModel @Inject constructor(
  private val repository: ProductRepository
) : BaseViewModel() {
  private val _productPaginate
      = MutableLiveData<ApiResource<DtoPaginate<ProductInfoDto>>>()
  private val _productDetail
      = MutableLiveData<ApiResource<ProductDetailDto>>()
  private val _productBarcode
      = MutableLiveData<ApiResource<BarcodeInfoDto>>()
  private val _productCreate
      = MutableLiveData<ApiResource<Nothing>>()
  private val _productUpdate
      = MutableLiveData<ApiResource<Nothing>>()
  private val _productAddToCounter
      = MutableLiveData<DbResource<Nothing>>()

  var status = MutableLiveData<Int>()
  val productPaginate: LiveData<ApiResource<DtoPaginate<ProductInfoDto>>>
      get() = _productPaginate
  val productDetail: LiveData<ApiResource<ProductDetailDto>>
      get() = _productDetail
  val barcodeInfo: LiveData<ApiResource<BarcodeInfoDto>>
      get() = _productBarcode
  val productCreate: LiveData<ApiResource<Nothing>>
      get() = _productCreate
  val productUpdate: LiveData<ApiResource<Nothing>>
      get() = _productUpdate
  val productAddToCounter: LiveData<DbResource<Nothing>>
      get() = _productAddToCounter

  fun listings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      repository.listings(current, pageSize).onStart {
        _productPaginate.postValue(ApiResource.Loading())
      }.catch {
        _productPaginate.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _productPaginate.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _productPaginate.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _productPaginate.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }

  fun get(id: String) {
    viewModelScope.launch {
      repository.get(id).catch {
        _productDetail.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _productDetail.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _productDetail.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _productDetail.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }

  fun create(
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) {
    viewModelScope.launch {
      repository.create(
        title,
        intro,
        price,
        cover,
      ).catch {
        _productCreate.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _productCreate.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _productCreate.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _productCreate.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }

  fun update(
    id: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) {
    viewModelScope.launch {
      repository.update(
        id,
        title,
        intro,
        price,
        cover,
      ).catch {
        _productUpdate.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _productUpdate.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _productUpdate.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _productUpdate.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }

  fun addToCounter(
    id: String,
    title: String,
    price: Float
  ) {
    viewModelScope.launch {
      repository.addToCounter(
        id,
        title,
        price
      ).onStart {
        _productAddToCounter.postValue(DbResource.Loading())
      }.catch {
        it.message?.let { message ->
          _productAddToCounter.postValue(DbResource.Error(message))
        }
      }.collect { result ->
        result.data.let {
          _productAddToCounter.postValue(DbResource.Success(it))
        }
      }
    }
  }
}

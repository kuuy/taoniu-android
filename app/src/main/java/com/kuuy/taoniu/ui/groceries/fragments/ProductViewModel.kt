package com.kuuy.taoniu.ui.groceries.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.ProductBarcodeDto
import com.kuuy.taoniu.data.groceries.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

import retrofit2.HttpException

import com.kuuy.taoniu.ui.base.BaseViewModel

@HiltViewModel
class ProductViewModel @Inject constructor(
  private val repository: ProductRepository
) : BaseViewModel() {

  private val _productListings
      = MutableLiveData<ApiResource<ProductListingsDto>>()
  private val _productDetail
      = MutableLiveData<ApiResource<ProductDetailDto>>()
  private val _productBarcode
      = MutableLiveData<ApiResource<ProductBarcodeDto>>()
  private val _productCreate
      = MutableLiveData<ApiResource<Nothing>>()
  private val _productUpdate
      = MutableLiveData<ApiResource<Nothing>>()
  private val _productAddToCounter
      = MutableLiveData<DbResource<Nothing>>()
  val productListings: LiveData<ApiResource<ProductListingsDto>>
      get() = _productListings
  val productDetail: LiveData<ApiResource<ProductDetailDto>>
      get() = _productDetail
  val productBarcode: LiveData<ApiResource<ProductBarcodeDto>>
      get() = _productBarcode
  val productCreate: LiveData<ApiResource<Nothing>>
      get() = _productCreate
  val productUpdate: LiveData<ApiResource<Nothing>>
      get() = _productUpdate
  val productAddToCounter: LiveData<DbResource<Nothing>>
      get() = _productAddToCounter

  fun getProductListings() {
    viewModelScope.launch {
      repository.getProductListings()
          .onStart {
            _productListings.postValue(ApiResource.Loading())
          }.catch {
            it.message?.let { message ->
              _productListings.postValue(ApiResource.Error(message))
              retryFunctionList.add(::getProductListings)
            }
          }.collect { response ->
            response.data.let {
              _productListings.postValue(ApiResource.Success(it))
            }
          }
    }
  }

  fun getProductDetail(id: String) {
    viewModelScope.launch {
      repository.getProductDetail(id)
          .onStart {
            _productDetail.postValue(ApiResource.Loading())
          }.catch {
            var code: Int = 0
            if (it is HttpException) {
              code = it.code()
            }
            it.message?.let { message ->
              _productDetail.postValue(ApiResource.Error(
                message,
                code,
              ))
              //retryFunctionList.add(::getProductDetail)
            }
          }.collect { response ->
            response.data.let {
              _productDetail.postValue(ApiResource.Success(it))
            }
          }
    }
  }

  fun getProductBarcode(barcode: String) {
    viewModelScope.launch {
      repository.getProductBarcode(barcode)
          .onStart {
            _productBarcode.postValue(ApiResource.Loading())
          }.catch {
            var code: Int = 0
            if (it is HttpException) {
              code = it.code()
            }
            it.message?.let { message ->
              _productBarcode.postValue(ApiResource.Error(
                message,
                code,
              ))
              //retryFunctionList.add(::getProductBarcode)
            }
          }.collect { response ->
            response.data.let {
              _productBarcode.postValue(ApiResource.Success(it))
            }
          }
    }
  }

  fun createProduct(
    barcode: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) {
    viewModelScope.launch {
      repository.createProduct(
        barcode,
        title,
        intro,
        price,
        cover,
      ).onStart {
        _productCreate.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _productCreate.postValue(ApiResource.Error(message))
              //retryFunctionList.add(::getProductBarcode)
        }
      }.collect { response ->
        response.data.let {
          _productCreate.postValue(ApiResource.Success(it))
        }
      }
    }
  }

  fun updateProduct(
    id: String,
    barcode: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) {
    viewModelScope.launch {
      repository.updateProduct(
        id,
        barcode,
        title,
        intro,
        price,
        cover,
      ).onStart {
        _productUpdate.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _productUpdate.postValue(ApiResource.Error(message))
              //retryFunctionList.add(::getProductBarcode)
        }
      }.collect { response ->
        response.data.let {
          _productUpdate.postValue(ApiResource.Success(it))
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

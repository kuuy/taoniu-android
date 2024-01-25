package com.kuuy.taoniu.ui.groceries.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.groceries.dto.FeedInfoDto
import com.kuuy.taoniu.data.groceries.repositories.FeedsRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val feedsRepository: FeedsRepository,
) : BaseViewModel() {
  private val _feedListings
      = MutableLiveData<ApiResource<DtoPaginate<FeedInfoDto>>>()

  fun getFeedListings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      feedsRepository.listings(current, pageSize).catch {
        _feedListings.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _feedListings.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _feedListings.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _feedListings.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }
}
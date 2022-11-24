package com.kuuy.taoniu.data.cryptos.mappings.tradingview

import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisSummaryDto
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisSummary

fun AnalysisSummaryDto.transform(): AnalysisSummary {
  return AnalysisSummary(
    buy,
    neutral,
    sell,
    recommendation,
  )
}

fun List<AnalysisInfoDto>.transform(): List<AnalysisInfo> {
  return this.map { it.transform() }
}

fun AnalysisInfoDto.transform(): AnalysisInfo {
  return AnalysisInfo(
    remoteId=id,
    symbol,
    null,
    summary.transform(),
    timestamp,
  )
}
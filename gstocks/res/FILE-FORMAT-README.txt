File format for Portfolio JSON : 

{ 
  "portfolioName" : "name of portfolio",
  "portfolioItems" : [ 
	{ "tickerSymbol" : "ticker symbol",
 	  "quantity" : Integer value of quantity, 
	  "purchaseAmount" : float value of purchase amount,
 	  "dateOfPurchase" : "DD-MM-YYYY HH:mm:ss" 
	} 
  ] 
}

File format for Strategy JSON:

{ 
  "strategyName" : "name of strategy", 
  "startDate": "Day, Month Date, YEAR HOUR:MINUTE:SECOND AM/PM TIMEZONE", 
  "frequency": frequency of investment in days, 
  "price": price to invest each time, 
  "commissionFee": commission fee of investment, 
  "stocks": { 
	"Stock ticker symbol": weightage for a total of 100 
	} 
}
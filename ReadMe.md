Java implementation of BestDeals Bank Interview test task. Guice and Jersey are used to implement the REST endpoints/Resources. Sample endpoints provided below:

# Calculate] - simple/compound
## [POST]
> http://localhost:7080/calculator-services/services/calculator?type=simple
>Input Json:
>{
>  "principle": 2000,
>  "noOfYears": 5,
>  "rate": 3
>}

# [PUT]
## http://localhost:7080/calculator-services/services/deal/clients/1
> [
>   {
> 	"principle": 2000,
> 	"noOfYears": 5,
> 	"rate": 3
>   },
>   {
>     "principle": 32000,
>     "noOfYears": 35,
>     "rate": 3
>   }  
> ]

# [GET]
## http://localhost:7080/calculator-services/services/deal/clients/1


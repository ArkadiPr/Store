var Controllers= angular.module('app');
//$.ajax({ async:false })


Controllers.controller('aboutController',['$scope', '$http','$routeParams', function ($scope, $http) {

    $scope.curPage = 1,
        $scope.itemsPerPage = 3,
        $scope.maxSize = 5;
    $scope.field=false;

   fillTable =async function () {
          await $http.get(contextPath + '/api/v1/books')
              .then(function (response) {
                  $scope.BooksList = response.data;
              });
          $scope.field=true;
          $scope.$apply();
    };
   fillTable();

    $scope.numOfPages = function () {
        if($scope.BooksList) {
            return Math.ceil($scope.BooksList.length / $scope.itemsPerPage);
        }
    };

    $scope.$watchGroup(['curPage+numPerPage','field'],  async function(oldVal,newVal,$scope) {
        console.log(oldVal+' '+newVal);
        if($scope.BooksList && oldVal!==newVal) {
            var begin = (($scope.curPage - 1) * $scope.itemsPerPage),
                end = begin + $scope.itemsPerPage;
            $scope.FilteredItems = $scope.BooksList.slice(begin, end);
        }
        else {
            $scope.FilteredItems = $scope.BooksList;
        }
    });
}]);



Controllers.controller('booksController',['$scope', '$http','$routeParams', function ($scope, $http) {
    $scope.curPage = 1,
        $scope.itemsPerPage = 3,
        $scope.maxSize = 5;
    $scope.field=false;

    fillTable = async function () {
        await  $http.get(contextPath + '/api/v1/books')
            .then(function (response) {
                $scope.BooksList = response.data;
                $scope.filteredItems=response.data;
            });
        $scope.field=true;
        $scope.$apply();
    };
    fillTable();

    $scope.numOfPages = function () {
        if($scope.BooksList) {
            return Math.ceil($scope.filteredItems.length / $scope.itemsPerPage);
        }
    };

    $scope.$watchGroup(['curPage+numPerPage','field','filteredItems'],  async function(oldVal,newVal,$scope) {
        console.log(oldVal+' '+newVal);
        if($scope.BooksList && oldVal!==newVal) {
            var begin = (($scope.curPage - 1) * $scope.itemsPerPage),
                end = begin + $scope.itemsPerPage;
            $scope.Items = $scope.filteredItems.slice(begin, end);
        }
        else {
            $scope.Items = $scope.BooksList;
        }
    });

    $scope.submitCreateNewBook = function () {
        $http.post(contextPath + '/api/v1/books', $scope.newBook)
            .then(function (response) {
                $scope.BooksList.push(response.data);
            });
    };
    $scope.submitFilterBooks=function(minPrice,maxPrice,titlePart,genre){
        $scope.minPrice=minPrice;
        $scope.maxPrice=maxPrice;
        $scope.titlePart=titlePart;
        $scope.filteredItems=$scope.BooksList;
        if (minPrice != null && minPrice!="") {
            $scope.filteredItems = $scope.BooksList.filter(function (book) {
                return book.price >= minPrice;
            })
        }
        if (maxPrice != null && maxPrice!="") {
            $scope.filteredItems = $scope.filteredItems.filter(function (book) {
                return book.price <= maxPrice;
            })
        }
        if (titlePart != null && titlePart!="") {
            $scope.filteredItems = $scope.filteredItems.filter(function (book) {
                return book.title.indexOf(titlePart) + 1;
            })
        }
        if(genre!=null){
            if(genre.detective&& genre.fantastic&&genre.fantasy){
            }else
            if(genre.detective&& genre.fantastic){
                $scope.filteredItems=$scope.filteredItems.filter(function(book){
                    return (book.genre=='DETECTIVE'||book.genre=='FICTION');
                })
            }else
            if(genre.detective&& genre.fantasy){
                $scope.filteredItems=$scope.filteredItems.filter(function(book){
                    return (book.genre=='DETECTIVE'||book.genre=='FANTASY');
                })
            }else
            if(genre.fantastic&& genre.fantasy){
                $scope.filteredItems=$scope.filteredItems.filter(function(book){
                    return (book.genre=='FICTION'||book.genre=='FANTASY');
                })
            }else
            if(genre.fantastic){
                $scope.filteredItems=$scope.filteredItems.filter(function(book){
                    return (book.genre=='FICTION');
                })
            }else
            if(genre.fantasy){
                $scope.filteredItems=$scope.filteredItems.filter(function(book){
                    return (book.genre=='FANTASY');
                })
            }else if(genre.detective){
                $scope.filteredItems=$scope.filteredItems.filter(function(book){
                    return (book.genre=='DETECTIVE');
                })
            }
        }
        $scope.$apply();
    }

}]);

angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';
    var currentPage = 1;

    $scope.loadInitCatalog = function () {
        $http({
            url: contextPath + "/products",
            method: 'GET',
            params: {
                page: 1
            }
        }).then(function (response) {
            $scope.productList = response.data;
            var arr = [];
            for(var i=0; i<response.data.totalPages;i++){
                    arr[i]=i+1;
            }
            $scope.countPage = arr;

        });
    };

    $scope.loadCatalog = function (page,min,max) {
        currentPage = page;
        $http({
            url: contextPath + "/products",
            method: 'GET',
            params: {
                page: page,
                min : min,
                max : max
            }
        }).then(function (response) {
            $scope.productList = response.data;
            var arr = [];
            for(var i=0; i<response.data.totalPages;i++){
                arr[i]=i+1;
            }
            $scope.countPage = arr;
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.loadCatalog();
            });
    };

    $scope.newProduct = function () {
        $http({
            url: contextPath + "/products",
            method: 'post',
            params: {
                title: $scope.newProd.title,
                coast: $scope.newProd.coast
            }
        }).then(function (response) {
            $scope.loadCatalog();
        });
    }

    $scope.findBetween = function () {
        $scope.loadCatalog(currentPage, $scope.range.min, $scope.range.max);
    };

    $scope.clearFormMinMax = function (){
        $scope.range.max=null;
        $scope.range.min=null;
        $scope.loadInitCatalog();
    }

    $scope.setCurrentPage = function (i){
        currentPage = i;
        if ($scope.range.min!=null || $scope.range.max!=null){
            $scope.findBetween();
        }
        else {
            $scope.loadCatalog(currentPage);
        }
    };

    $scope.loadInitCatalog();

});
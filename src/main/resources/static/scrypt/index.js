angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';
    let curentPage = 0;

    $scope.loadInitCatalog = function () {
        $http({
            url: contextPath + "/products",
            method: 'GET',
            params: {
                page: curentPage
            }
        }).then(function (response) {
            $scope.productList = response.data;
            $scope.getCountPages();
        });
    };

    $scope.loadCatalog = function (page) {
        curentPage = page;
        page = page - 1;
        $http({
            url: contextPath + "/products",
            method: 'GET',
            params: {
                page: page
            }
        }).then(function (response) {
            $scope.productList = response.data;
        });
    };

    $scope.getCountPages = function () {
        $http.get(contextPath + '/getPagesCount')
            .then(function (response) {
                var pageCount=[];
                for (let i=0;i<response.data;i++){
                    pageCount[i]=i+1;
                }
                $scope.countPage = pageCount;

            });
    };

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)
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
        $http({
            url: contextPath + "/products/findBetween",
            method: 'GET',
            params: {
                max: $scope.range.max,
                min: $scope.range.min
            }
        }).then(function (response) {
            $scope.productList = response.data;
            console.info(response.data);
        });
    };

    $scope.loadInitCatalog();

});
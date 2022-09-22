angular.module('store-front').controller('storeController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/gateway/api/v1/products';
    let currentPage = 1;
    let lastPage = 0;
    let filterOn = false;

    $scope.loadCatalog = function (page, min, max) {
        currentPage = page;
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                page: page,
                min: min,
                max: max
            }
        }).then(function (response) {
            $scope.productList = response.data;
            lastPage = response.data.totalPages;
            var arr = [];
            for (var i = 0; i < lastPage; i++) {
                arr[i] = i + 1;
            }
            $scope.countPage = arr;
            $scope.getCartCount();
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/' + productId)
            .then(function (response) {
                $scope.loadCatalog(lastPage);
            });
    };

    $scope.newProduct = function () {
        $http.post(contextPath, $scope.newProd)
            .then(function (response) {
                $scope.loadCatalog(lastPage);
            });
    };

    $scope.findBetween = function () {
        filterOn = true;
        $scope.loadCatalog($scope.currentPage, $scope.range.min, $scope.range.max);
    };

    $scope.clearFormMinMax = function () {
        filterOn = false;
        $scope.range.max = null;
        $scope.range.min = null;
        $scope.loadCatalog(1);
    };

    $scope.setCurrentPage = function (i) {
        currentPage = i;
        if (filterOn) {
            $scope.findBetween();
        } else {
            $scope.loadCatalog(currentPage);
        }
    };

    $scope.toCart = function (productId) {
        $http({
            url: 'http://localhost:5555/cart/api/v1/cart/' + productId,
            method: 'POST',
            data: {cartName: $localStorage.cartName}
        }).then(function (response) {
            $scope.loadCatalog(currentPage);
        });
    };

    $scope.loadCatalog(currentPage);
});
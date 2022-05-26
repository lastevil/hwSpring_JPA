angular.module('app', []).controller('cartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.loadCart = function () {
        $http.get(contextPath + '/products/cart')
            .then(function (response) {
                $scope.productList = response.data;
                let Sum=0;
                $scope.each($scope.productList,(function (key,value){
                    let a =0;
                        if(key='coast'){
                            a=value;
                        }
                        if(key='count'){
                            a=a*value;
                        }
                        Sum+Sum+a;
                }));
                $scope.cartSum = Sum;
            });
    };

    $scope.fromCart = function (productId){
        $http.delete(contextPath + '/products/cart/' + productId)
            .then(function (response) {
                $scope.loadCart();
            })
    };

    $scope.AllFromCart = function (productId){
        $http.delete(contextPath + '/products/cartdeleteall' + productId)
            .then(function (response) {
                $scope.loadCart();
            })
    };

    $scope.addToCart = function (productId) {
        $http.post(contextPath + '/products/cart/'+ productId)
            .then(function (response) {
                $scope.loadCart();
            })
    };

    $scope.loadCart();
});
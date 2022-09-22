angular.module('store-front').controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1/order';
    $scope.getOrders = function () {
        $http.get(contextPath)
            .then(function (response) {
                $scope.ordersList = response.data;
            });
    };

    $scope.deleteOrder = function (id){
        $http.delete(contextPath+'/'+id)
            .then(function (response){
                $scope.getOrders();
            });
    };

    $scope.payOrder = function (id){
      $http.post(contextPath+'/pay/'+id)
          .then(function (){
              $scope.getOrders();
          });
    };

    $scope.getOrderItems = function (id) {
        $http.get(contextPath + '/' + id)
            .then(function (response){
                $scope.itemsList = response.data;
            });
    };

    $scope.getOrders();
});
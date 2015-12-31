(function () {
    "use strict";

    angular.module("jukeB", ["angular-carousel", "ngRoute", "restangular", "ngDialog", "ngAnimate", "ui.bootstrap", "snap"]);

    angular.module("jukeB").controller("jukeBController", ["$scope",  function($scope){
        $scope.teste = "hello world";
        /*
         Restangular.one('forms').one('products', 0).get().then(function(product){
         console.log(product);
         })
         */

    }]);


    angular.module("jukeB").config(["$routeProvider", function ($routeProvider, $scope) {
        $routeProvider
            .when("/", {
                redirectTo: "/vilma"
            })
            .when("/:partyid/", {
                controller: "partyController",
                templateUrl: "html/partyhome.html"
            })

            .otherwise({
                redirectTo: "/vilma"
            })

    }]);//fim config
})();//fim
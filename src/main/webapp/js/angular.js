var app = angular.module('app', []);

app.controller('postController', function($scope, $http, $location) {
	$scope.submitForm = function(){
		var url = $location.absUrl() + "/api/lockssdashboard/boxes";
		
		var config = {
                headers : {
                    'Content-Type': 'application/json;charset=utf-8;'
                }
        }
		
		var data = {
            ipAddress: $scope.ipAddress,
            v3identity: $scope.v3identity
        };
		
		
		$http.post(url, data, config).then(function (response) {
			$scope.postResultMessage = "Sucessful!";
		}, function (response) {
			$scope.postResultMessage = "Fail!";
		});
		
		$scope.ipaddress = "";
		$scope.v3identity = "";
	}
});

app.controller('getallLOCKSSBoxesController', function($scope, $http, $location) {
	
	$scope.showAllLOCKSSBoxes = false;

	$scope.getAllLOCKSSBoxes = function() {
		var url = $location.absUrl() + "/api/lockssdashboard/boxes";

		var config = {
			headers : {
				'Content-Type' : 'application/json;charset=utf-8;'
			}
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.allLOCKSSBoxes = response.data;
				$scope.showAllLOCKSSBoxes = true;

			} else {
				$scope.getResultMessage = "get All LOCKSS Boxes Data Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});

	}
});

app.controller('getLOCKSSBoxController', function($scope, $http, $location) {
	
	$scope.showLOCKSSBox = false;
	
	$scope.getLOCKSSBox = function() {
		var url = $location.absUrl() + "/api/lockssdashboard/boxes/" + $scope.LOCKSSBoxId;

		var config = {
			headers : {
				'Content-Type' : 'application/json;charset=utf-8;'
			}
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.LOCKSSBox = response.data;
				$scope.showLOCKSSBox = true;

			} else {
				$scope.getResultMessage = "LOCKSS Box Data Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});

	}
});

app.controller('getLOCKSSBoxesByIpAddressController', function($scope, $http, $location) {
	
	$scope.showLOCKSSBoxByIpAddress = false;
	
	$scope.getLOCKSSBoxByIpAddress = function() {
		var url = $location.absUrl() + "/api/lockssdashboard/boxes/findbyIpAddress";

		var config = {
			headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
		
			params: { 'ipAddress' : $scope.LOCKSSBoxIpAddress }
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.allLOCKSSBoxByIpAddress = response.data;
				$scope.showLOCKSSBoxByIpAddress = true;

			} else {
				$scope.getResultMessage = "LOCKSS Box Data Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});

	}
});

app.controller('getAvailableLOCKSSBoxesController', function($scope,$http,$location){
		
	$scope.showAvailableLOCKSSBoxes = false;
	$scope.LOCKSSConfigUrl="https://lockssadmin.ulb.ac.be/lockss.xml"
	$scope.getAvailableLOCKSSBoxes = function() {
		var url = $location.absUrl() + "/api/lockssnetwork/boxes";

		var config = {
			headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
			params: { 'configUrl' : $scope.LOCKSSConfigUrl }
		}
		
		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.availableLOCKSSBoxes = response.data;
				$scope.showAvailableLOCKSSBoxes = true;

			} else {
				$scope.getResultMessage = "Could not get Box IP address Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
	}
	
});


app.controller('MainCtrl', function($scope, utils) {

	this.$onInit = function () {
	
	$scope.allLOCKSSBoxes = [], 
    $scope.availableLOCKSSBoxes = [];
	
	var url = $location.absUrl() + "/api/lockssdashboard/boxes";

		var config = {
			headers : {
				'Content-Type' : 'application/json;charset=utf-8;'
			}
		}

		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.allLOCKSSBoxes = response.data;
				$scope.showAllLOCKSSBoxes = true;

			} else {
				$scope.getResultMessage = "get All LOCKSS Boxes Data Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});
	

	url = $location.absUrl() + "/api/lockssnetwork/boxes";

	config = {
			headers : {	'Content-Type' : 'application/json;charset=utf-8;' },
			params: { 'configUrl' : $scope.LOCKSSConfigUrl }
		}
		
		$http.get(url, config).then(function(response) {

			if (response.data.status == "Done") {
				$scope.availableLOCKSSBoxes = response.data;
				$scope.showAvailableLOCKSSBoxes = true;

			} else {
				$scope.getResultMessage = "Could not get Box IP address Error!";
			}

		}, function(response) {
			$scope.getResultMessage = "Fail!";
		});

	}

    
  })
  .factory('utils', function Utils() {
    return {
      insertData: function (list, numItems) {
        for(var i = 0; i < numItems; i++) {
          list.push({id: i + 1, title: 'item' + (i + 1)});  
        }  
      },
      getIndexesFromList: function(list) {
        var newList = [];
        for(var i in list) {
          if(typeof list[i].id === "number" && newList.indexOf(list[i].id) === -1) newList.push(list[i].id)
        }
        return newList;
      },
      getAllSelectedItems: function(list) {
        var newList = [];
        newList = list.filter(function(el) {
              return el.active === true;
        });
        return newList;
      },
      addListIfNoExists: function(availableLOCKSSBoxes, newListToAppend) {
        var indexes = this.getIndexesFromList(availableLOCKSSBoxes);
        var newList = [];
        for(var i in newListToAppend) {
          if(indexes.indexOf(newListToAppend[i].id) === -1) availableLOCKSSBoxes.push(newListToAppend[i])
        }
        return availableLOCKSSBoxes;
      }
    }
  })
  .directive('dualList', function(utils) {
    
    function _controller ($scope) {
      $scope.selectAllItem = function(list, checked) {
        list.map(function(item) {
           item.active = checked
          return item;
        });
      };
      $scope.getAllSelectedItems = function(list) {
        return utils.getAllSelectedItems(list);
      }
      $scope.moveItemToRightList = function() {
        var newListToAppend = $scope.allLOCKSSBoxes.filter(function(el) {
            if(el.active === true) {
                el.active = false;
                return el;
            }
        });
        if (newListToAppend.length > 0) {
           $scope.allLOCKSSBoxes = $scope.allLOCKSSBoxes.filter(function (el) {
              return utils.getIndexesFromList(newListToAppend).indexOf(el.id) === -1;
            });
            $scope.availableLOCKSSBoxes = utils.addListIfNoExists($scope.availableLOCKSSBoxes, newListToAppend);
            if($scope.allLOCKSSBoxes.length === 0) $scope.checked1 = false;
        }
       
      };
      $scope.moveItemToLeftList = function() {
        var newListToAppend = $scope.availableLOCKSSBoxes.filter(function(el) {
            if(el.active === true) {
                el.active = false;
                return el;
            }
        });
        if (newListToAppend.length > 0) {
            $scope.availableLOCKSSBoxesavailableLOCKSSBoxes = $scope.availableLOCKSSBoxes.filter(function (el) {
              return utils.getIndexesFromList(newListToAppend).indexOf(parseInt(el.id)) === -1;
            });
            $scope.allLOCKSSBoxes = utils.addListIfNoExists($scope.allLOCKSSBoxes, newListToAppend);
            if($scope.availableLOCKSSBoxes.length === 0) $scope.checked2 = false; 
        }
       
      };
    }
    return {
      restrict: "E",
      scope: true,
      controller: _controller
     
    };
  });

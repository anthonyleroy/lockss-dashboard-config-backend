<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>LOCKSS dashboard configurator</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.6.0/angular.min.js"></script>
<script src="/js/angular.js"></script>

<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" />
</head>
<body>
	<div class="container" ng-app="app" >


		<h1>LOCKSS dashboard configurator</h1>

		<!-- 		<div class="row"> -->
		<!-- 			<div ng-controller="postController" class="col-md-3"> -->
		<!-- 				<form name="LOCKSSBoxForm" ng-submit="submitForm()"> -->
		<!-- 					<label>IPAddress</label> -->
		<!-- 					<input type="text" name="ipaddress"	class="form-control" ng-model="ipaddress" /> -->
		<!-- 					<label>v3Identity</label> -->
		<!-- 					<input type="text" name="v3identity" class="form-control" ng-model="v3identity" /> -->

		<!-- 					<button type="submit" class="btn btn-primary">Submit</button> -->
		<!-- 				</form> -->
		<!-- 				<p>{{postResultMessage}}</p> -->
		<!-- 			</div> -->
		<!-- 		</div> -->


		<hr class="mt-2 mb-3" />




		<div class="row" ng-controller="MainCtrl">
			<div ng-controller="getAvailableLOCKSSBoxesController"
				class="dual-list list-left col-md-5">
				<div class="well text-left">
					<div class="row">
						<h3>Available boxes in lockss.xml</h3>
						<div class="col-md-3">
							<div class="checkbox">
								<label> <input type="checkbox" ng-model="checked1"
									ng-click="selectAllItem(availableLOCKSSBoxes.data, checked1)"> Selected
									{{getAllSelectedItems(availableLOCKSSBoxes.data).length}}/{{availableLOCKSSBoxes.data.length}}
								</label>
							</div>
						</div>
						<div class="col-md-9">
							<div class="input-group">
								<span class="input-group-addon glyphicon glyphicon-search"></span>
								<input type="text" name="SearchDualList" ng-model="search1"
									class="form-control" placeholder="search" />
							</div>
						</div>
					</div>

					<div class="row">

						<div class="col-md-9">

							<input type="text" class="form-control" style="width: 300px;"
								ng-model="LOCKSSConfigUrl" [(ngModel)]="LOCKSSConfigUrl" /> <br />
						</div>

						<div class="col-md-3">
							<button ng-click="getAvailableLOCKSSBoxes()"
								class="btn btn-primary">Refresh</button>
						</div>

					</div>
					<div class="row">

						<ul class="list-group">
							<a class="list-group-item" href="" data-id="{{availableBox.id}}"
								ng-click="(availableBox.active = !availableBox.active)"
								ng-class="{active: availableBox.active}"
								ng-repeat="availableBox in availableLOCKSSBoxes.data|filter: search1">{{availableBox.ipAddress}}</a>
						</ul>
						<p ng-if="(availableBox | filter:search1).length == 0">No Data</p>

					</div>
				</div>

			</div>



			<div class="list-arrows col-md-1 text-center">
				<button ng-click="moveItemToLeftList()"
					class="btn btn-default btn-sm move-left">
					<span class="glyphicon glyphicon-chevron-left"></span>
				</button>

				<button ng-click="moveItemToRightList()"
					class="btn btn-default btn-sm move-right">
					<span class="glyphicon glyphicon-chevron-right"></span>
				</button>
			</div>


			<div ng-controller="getallLOCKSSBoxesController"
				class="dual-list list-right col-md-5">
				<div class="well">
					<div class="row">
						<h3>Boxes in dashboard</h3>

						<div class="col-md-3">
							<div class="checkbox">
								<label> <input type="checkbox" ng-model="checked2"
									ng-click="selectAllItem(allLOCKSSBoxes, checked2)"> Selected
									{{getAllSelectedItems(allLOCKSSBoxes).length}}/{{allLOCKSSBoxes.length}}
								</label>
							</div>
						</div>
						<div class="col-md-9">
							<div class="input-group">
								<span class="input-group-addon glyphicon glyphicon-search"></span>
								<input type="text" name="SearchDualList" ng-model="search2"
									class="form-control" placeholder="search" />
							</div>
						</div>
					</div>

					<button ng-click="getAllLOCKSSBoxes()" class="btn btn-primary">Refresh</button>


					<ul class="list-group">
						<a class="list-group-item" href="" data-id="{{lockssbox.id}}"
							ng-click="lockssbox.active = !lockssbox.active"
							ng-class="{active: lockssbox.active}"
							ng-repeat="lockssbox in allLOCKSSBoxes|filter: search2">{{
							lockssbox.ipAddress }}</a>
					</ul>
					<p ng-if="(allLOCKSSBoxes.data | filter:search2).length == 0">No Data</p>
					<p>{{getResultMessage}}</p>
				</div>
			</div>
		</div>













		<!--                 <div ng-controller="getallLOCKSSBoxesController" class="col-md-6"> -->

		<!-- 				<button ng-click="getAllLOCKSSBoxes()" class="btn btn-primary">Get Boxes in Dashboard</button> -->

		<!-- 				<div ng-show="showAllLOCKSSBoxes"> -->
		<!-- 					<ul class="list-group"> -->

		<!-- 					<li class="list-group-item" ng-repeat="lockssbox in allLOCKSSBoxes.data"> -->
		<!-- 						{{ lockssbox.ipAddress }} -->
		<!-- 					</li> -->

		<!-- 					</ul> -->
		<!-- 				</div> -->
		<!-- 				<p>{{getResultMessage}}</p> -->
		<!-- 				</div> -->



		<div class="row">

			<div ng-controller="getLOCKSSBoxController" class="col-md-3">
				<h3>Customer by ID</h3>

				<input type="text" class="form-control" style="width: 100px;"
					ng-model="LOCKSSBoxId" /> <br />
				<button ng-click="getLOCKSSBox()">Get LOCKSS Box</button>

				<div ng-show="showLOCKSSBox">
					Id: {{lockssbox.data.id}}<br /> IP Address:
					{{lockssbox.data.ipAddress}}<br /> v3identity:
					{{lockssbox.data.v3Identity}}
				</div>
				<p>{{getResultMessage}}</p>
			</div>

			<div ng-controller="getLOCKSSBoxesByIpAddressController"
				class="col-md-4">
				<h3>LOCKSS Box by IP Address</h3>

				<input type="text" class="form-control" style="width: 100px;"
					ng-model="lockssboxIPaddress" /><br />
				<button ng-click="getLOCKSSBoxesByIpAddress()">Get LOCKSS
					box</button>

				<div ng-show="showLOCKSSBoxesByIpAddress">

					<ul class="list-group">
						<li ng-repeat="lockssbox in allLOCKSSBoxByIpAddress.data"><h4
								class="list-group-item">
								<strong>LOCKSS Box {{$index}}</strong><br /> Id:
								{{lockssbox.id}}<br /> IP address: {{lockssbox.ipAddress}}<br />
								v3identity: {{lockssbox.v3Identity}}
							</h4></li>
					</ul>
				</div>
				<p>{{getResultMessage}}</p>
			</div>

		</div>
	</div>


</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Route Finder</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="http://localhost:8080/geoserver/openlayers3/ol.css" type="text/css">
  <link rel="stylesheet" href="css/style_main.css" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="http://localhost:8080/geoserver/openlayers3/ol.js" type="text/javascript"></script>
  <script type="text/javascript" src="scripts/timing.js"></script>
  
  <!--
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <script src="scripts/jquery-3.2.0.min.js"></script>
  <script src="scripts/bootstrap.min.js"></script>
-->


</head>
<body>

<!-- navigaton bar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <!--
      <div class="navbar-brand" style="vertical-align: center; padding-top: 4px;">
          <span class="navbar-nav" style="vertical-align: top; padding-top: 0px;"><img src="images/site_logo.png" width="40px" height="40px">&nbsp;My&nbsp;Company</span>
      </div>
      -->
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Logo</a>
    </div>

    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="" data-toggle="modal" data-target="#signup_modal"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
        <li><a href="" data-toggle="modal" data-target="#login_modal"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul></div>
  </div>
</nav>
<!-- navigation bar end -->

<!-- login Modal -->
<div id="login_modal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 style="">Login</h4>
        </div>
        <div class="modal-body">
          <form role="form">
            <div class="form-group" id="login_email_div">
              <label for="login_email"><span class="glyphicon glyphicon-user"></span> Username</label>
              <input type="text" class="form-control" id="login_email" placeholder="Enter Email" data-toggle="popover" data-placement="bottom" data-content="Enter valid Email address" data-trigger="manual">
              
            </div>
            <div class="form-group"  id="login_pass_div">
              <label for="login_pass"><span class="glyphicon glyphicon-lock"></span> Password</label>
              <input type="password" class="form-control" id="login_pass" placeholder="Enter password" data-toggle="popover" data-placement="bottom" data-content="Enter Password" data-trigger="manual">

            </div>
            <div class="checkbox">
              <label><input type="checkbox" value="" checked>Remember me</label>
            </div>
            <button type="button" id="login_login" class="btn btn-default btn-success btn-block">Login</button>
          </form>
        </div>
        <div class="modal-footer">
          <div class="pull-left">
          <p align="left">Not a member? <a href="#">Sign Up</a></p>
          <p align="left">Forgot <a href="#">Password?</a></p>
          </div>
          <button type="submit" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
        </div>
    </div>
  </div>
</div>
<!-- Login modal End -->
<!-- Sign Up Modal -->
<div id="signup_modal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 style="">Sign Up</h4>
        </div>
        <div class="modal-body">
          <form role="form">
            <div class="form-group" id="signup_uname_div">
              <label for="signup_uname"><span class="glyphicon glyphicon-user"></span> Username</label>
              <input type="text" class="form-control" id="signup_uname" placeholder="Enter Username" data-toggle="popover" data-placement="bottom" data-content="Username must be 3 to 10 Charactors long." data-trigger="manual">
            </div>
            <div class="form-group" id="signup_email_div">
              <label for="signup_email"><span class="glyphicon glyphicon-envelope"></span> Email</label>
              <input type="text" class="form-control" id="signup_email" placeholder="Enter Email" data-toggle="popover" data-placement="bottom" data-content="Enter valid Email address" data-trigger="manual">
            </div>
            <div class="form-group"  id="signup_pass_div">
              <label for="signup_pass"><span class="glyphicon glyphicon-lock"></span> Password</label>
              <input type="password" class="form-control" id="signup_pass" placeholder="Enter password" data-toggle="popover" data-placement="bottom" data-content="Enter Password" data-trigger="manual">
            </div>
            <div class="form-group"  id="signup_repass_div">
              <label for="signup_repass"><span class="glyphicon glyphicon-lock"></span> Password</label>
              <input type="password" class="form-control" id="signup_repass" placeholder="Re-Enter password" data-toggle="popover" data-placement="bottom" data-content="Passwords are not match." data-trigger="manual">
            </div>
            <button type="button" id="signup_signup" class="btn btn-default btn-success btn-block">Sign Up</button>
          </form>
        </div>
        <div class="modal-footer">
          <div class="pull-left">
          <p align="left">Already a member? <a href="#">Login</a></p>
          <p align="left">Read <a href="#">Teams and Conditions</a></p>
          </div>
          <button type="submit" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
        </div>
    </div>
  </div>
</div>
<!-- Sign up modal End -->

<!-- side panels -->
<div class="container-fluid">
    <!-- left panel -->
    <div id="left_slideout" class="slideout">
      <span id="left_icon" class="glyphicon glyphicon-menu-right"></span>
    </div>
    <div id="left_slideout_inner" class="slideout_inner">
      <div class="panel panel-default">
        <!-- route finder -->
        <div class="panel-heading">Find the Route</div>
        <div class="panel-body">
          <form role="form">
            <div class="form-group" id="source_div">
              <label for="source" class="col-md-10"><span class="glyphicon glyphicon-screenshot"></span> Start Place&nbsp;</label>
              <div align="right" class="col-md-2"><span class="glyphicon glyphicon-pushpin" id="get_sorce"></span></div>
              <input type="text" autocomplete="off" class="form-control" id="source" placeholder="Search source.." data-toggle="popover" data-placement="bottom" data-content="Enter strat place." data-trigger="manual">

              <div id="source_search_group" hidden>
                <div id="s0" class="source-search-result" hidden>1</div>
                <div id="s1" class="source-search-result" hidden>2</div>
                <div id="s2" class="source-search-result" hidden>3</div>
                <div id="s3" class="source-search-result" hidden>4</div>
                <div id="s4" class="source-search-result" hidden>5</div>
              </div>
            </div>

            <div class="form-group" id="destination_div">
              <label for="destination" class="col-md-10"><span class="glyphicon glyphicon-screenshot"></span> Destination &nbsp;</label>
              <div align="center" class="col-md-2 pinpoint_marker"><span class="glyphicon glyphicon-pushpin" id="get_destination"></span></div>
              <input type="text" autocomplete="off" class="form-control" id="destination" placeholder="Search Destination.." data-toggle="popover" data-placement="bottom" data-content="Enter Destination." data-trigger="manual">
            	<div id="destination_search_group" hidden>
                <div id="s5" class="destination-search-result" hidden>1</div>
                <div id="s6" class="destination-search-result" hidden>2</div>
                <div id="s7" class="destination-search-result" hidden>3</div>
                <div id="s8" class="destination-search-result" hidden>4</div>
                <div id="s9" class="destination-search-result" hidden>5</div>
              </div>
            </div>
            
            <div align="center">
              <button type="button" class="btn btn-default" id="findRoute">Find</button>
            </div>
            </form>
        </div>
      </div>
      <!-- route finder end -->

      <!-- Traffic updater -->
      <div class="panel panel-default">
        <div class="panel-heading">Update Traffic</div>
        <div class="panel-body">
          <form role="form">
            <div class="form-group" id="traffic_div">
              <label for="route_id" class="col-md-10"><span class="glyphicon glyphicon-screenshot"></span> Route Id&nbsp;</label>
              <div align="right" class="col-md-2"><span class="glyphicon glyphicon-pushpin" id="get_route_id"></span></div>
              <input type="text" autocomplete="off" class="form-control" id="route_id" placeholder="Route Id" data-toggle="popover" data-placement="bottom" data-content="Enter Route ID" data-trigger="manual">
            </div>

            <div class="form-group" id="login_email_div">
              <label for="traffic_level" class="col-md-10"> Traffic Level &nbsp;</label>
              <select class="form-control" id="traffic_level">
                  <option value="1">Low</option>
                  <option value="3">Moderate</option>
                  <option value="5">High</option>
                  <option value="1000">Blocked</option>
              </select>
              
            </div>
            <div align="center">
              <button type="button" class="btn btn-default" id="updateTraffic">Update</button>
            </div>
            </form>
        </div>
      </div>
      <!-- traffic updater end -->
    </div>
    <!-- left panel end -->


    <!-- right panel -->
    <div id="right_slideout" class="slideout">
      <span id="right_icon" class="glyphicon glyphicon-menu-left"></span>
    </div>
    <div id="right_slideout_inner" class="slideout_inner">
      <div class="panel panel-default">
        <div class="panel-heading">Panel Heading</div>
        <div class="panel-body">Panel Content</div>
      </div>
    </div>
    <!-- right panel end -->
</div>
<!-- side panels end -->
<!-- side panels script -->  
<script type="text/javascript" src="scripts/components.js"></script>
<!-- side panels script end -->


<!-- map divisions -->
<div id="map"></div>
<div id="location" align="center" style=""></div>
<div id="turn" hidden>152</div>
<div id="source_pin" hidden><span class="glyphicon glyphicon-map-marker"></span></div>
<div id="destination_pin" hidden><span class="glyphicon glyphicon-map-marker"></span></div>
<!-- map divisions end -->
<!-- map division script -->
<script type="text/javascript" src="scripts/map_rendering.js"></script>
<script type="text/javascript" src="scripts/map_processing.js"></script>

<!-- map division script end-->
</body>
</html>

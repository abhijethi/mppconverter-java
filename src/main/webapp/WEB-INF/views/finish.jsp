<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!doctype html>
<html lang="en" >
<head>
  <title>${title }</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css"
    rel="stylesheet">
  <link href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-responsive.min.css"
    rel="stylesheet">
    <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/3.1.0/css/font-awesome.min.css"
    rel="stylesheet">
<!--[if IE 7]>
  <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/3.1.0/css/font-awesome-ie7.min.css"
  rel="stylesheet">
<![endif]-->

    <style>
            .topcontainer {
                margin-top : 50px;
            }
    </style>
</head>

<body>
<div class="navbar navbar-fixed-top navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
        <button type="button" class="btn btn-navbar"
                data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <!--  start of navbar entry -->
        <a class="brand" href="/"><i class="icon-filter icon-white"></i>
            ${title }
        </a>
        </div>
    </div>
</div>

<div class="container topcontainer" ng-app="mpp">
    <div class="row-fluid">
        <h1>${subtitle}</h1>
        <hr />

        <c:if test="${error != null}">
        <div class="alert alert-error">
            <i class="icon-thumbs-down icon-2x pull-left"></i> ${error}
        </div>
        </c:if>

        <c:if test="${success != null}">
        <div class="alert alert-success">
            <i class="icon-thumbs-up icon-2x pull-left"></i> ${success}
        </div>
        </c:if>

        <c:if test="${attachmenturl != null}">
        <a href="${attachmenturl}" class="btn btn-warning">
            <i class="icon-external-link icon-2x icon-white"></i>
            See the File Record in Salesforce
        </a>
        </c:if>

        <a href="${returnURL}" class="btn btn-warning">
            <i class="icon-external-link icon-2x icon-white"></i>
            Back to Project Page
        </a>
    </div>
</div>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

</body>
</html>

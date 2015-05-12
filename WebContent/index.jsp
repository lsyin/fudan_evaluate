<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>NLPCC-Evaluate</title>

<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">

</head>
<body>
<%@ include file="header.jspf"%>
<hr />
<div class="container">
 <h2 align="center">Shared Task in NLPCC 2015:<br/>Chinese Word Segmentation and POS Tagging for Weibo Text</h2>
              
 <h3 id="1">1 Introduction</h3> 
<p>Word segmentation and Part-of-Speech (POS) tagging are two fundamental tasks for Chinese language processing.In recent years, word segmentation and POS tagging have undergone great development. The popular method is to regard these two tasks as sequence labeling problem, which can be handled with supervised learning algorithms such as Conditional Random Fields (CRF).<br>
However, the performances of the state-of-the-art systems are still relatively low for the informal texts, such as micro-blogs, forums.<br>
In this shared task, we wish to investigate the performances of Chinese word segmentation and POS tagging for the micro-blog texts.</p>


<h3 id="2">2 Description of the Task</h3>
<h4 class="subtitle">2.1 Subtasks </h4>
<p>This task focus the two fundamental problems of Chinese language processing: word segmentation and POS tagging, which can be divided into two subtasks:<br>
1. Chinese word segmentation<br>
2. Joint Chinese word segmentation and POS Tagging
</p>
<h4 class="subtitle">2.2 Tracks </h4>
<p>Each participant will be allowed to submit the three runs for each subtask: closed track run, semi-open track run and open track run.</p>
<p>1. In the closed track, participants could only use information found in the provided training data.<br>
Information such as externally obtained word counts, part of speech information, or name lists was excluded.</p>
<p>2. In the semi-open track, participants could use the information extracted from the provided background data in addition to the provided training data.<br>
Information such as externally obtained word counts, part of speech information, or name lists was excluded.</p>
<p>3. In the open track, participants could use the information which should be public and be easily obtained.<br>
But it is not allowed to obtain the result by the manual labeling or crowdsourcing way.</p>

<h3 id="3">3 Data</h3>
<p>Different with the popular used news dataset, we use relatively informal texts from Sina Weibo1. The training and test data consist of micro-blogs from various topics, such as finance, sports, entertainment, and so on.</p>
<p>The data are collected from Sina Weibo. Both the training and test files are UTF-8 encoded. The information of dataset is shown in Table 1.</p>
<p>There are total 36 POS tags in this dataset. A detailed list of POS tags is shown in Table 2.</p>
<h4 class="subtitle">3.1 Background Data</h4>
<p>Besides the training data, we also provide the background data, from which the training and test data are drawn.<br>
The purpose is to find the more sophisticated features by the unsupervised way.</p>

<h3 id="4">4 Evaluation Metrics</h3>
<p>We use the standard SIGHAN bake-off scoring program to calculate precision, recall,F1-score and out-of-vocabulary (OOV) word recall.</p>
<hr />
<table class="table">
<caption align="bottom">Table 1: Statistical information of dataset.</caption> 
	<thead>
		<tr>
			<th>Dataset</th>
			<th>Sents</th>
			<th>Words</th>
			<th>Chars</th>
			<th>Word Types</th>
			<th>Char Types</th>
			<th>OOV Rate</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Training</td>
			<td>10,000</td>
			<td>215,567</td>
			<td>348,551</td>
            <td>28,355</td>
            <td>39,73</td>
            <td>-</td>
		</tr>
		<tr>
			<td>Test</td>
			<td>5,000</td>
			<td>106,843</td>
			<td>172,342</td>
            <td>18,785</td>
            <td>3,540</td>
            <td>9.75%</td>
		</tr>
		<tr>
			<td>Total</td>
			<td>15,000</td>
			<td>322,410</td>
			<td>520,555</td>
            <td>35,277</td>
            <td>4,243</td>
            <td>-</td>
		</tr>	
	</tbody>
</table>
</div>
</body>
</html>
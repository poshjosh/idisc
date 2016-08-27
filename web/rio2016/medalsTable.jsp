<%-- 
    Document   : rio2016medalsTable
    Created on : Aug 11, 2016, 11:47:46 PM
    Author     : Josh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_without_slider   
    pageTitle="Rio 2016 Medals Table - ${appName}" 
    pageKeywords="rio2016,medals table" 
    pageDescription="Rio 2016 Medals Table">
    <jsp:attribute name="pageHeadInclude" trim="true">
        <style type="text/css">
            .medals{ background-color:turquoise; }
            .table-medal-countries__link-table{ background-color:aqua; }
        </style>    
    </jsp:attribute>
    <jsp:attribute name="pageContent" trim="true">
        <p><a href="${contextURL}/rio2016/expandedMedalsTable.jsp">View expanded Medals Table</a></p>
        <small>
            <div class="table-count" id="ms-countries-medal-count">
<table>
			<thead>
				<tr class="medals">
					<td tabindex="-1" colspan="3" class="visible"></td>
					
					<td><span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span></td>
					<td><span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span></td>
					<td><span class="medal sprite-ui sprite-ui--medal-bronze-typed" title="Bronze" aria-label="Bronze">Bronze</span></td>
					<td tabindex="-1"><strong>TOTAL</strong></td>
				</tr>
			</thead>
			<tbody>
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="USA" >
                            <td class="col-1">
                                <strong><strong>1</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="United States" aria-label="United States" class="flag sprite-flags sprite-flags--USA"></span>
                                <span class="country">USA</span>
                            </td>
                            <td class="col-3"><span class="country">United States</span></td>
                            <td class="col-4">46</td>
                            <td class="col-5">37</td>
                            <td class="col-6">38</td>
                            <td class="col-7"><strong>121</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="GBR" >
                            <td class="col-1">
                                <strong><strong>2</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Great Britain" aria-label="Great Britain" class="flag sprite-flags sprite-flags--GBR"></span>
                                <span class="country">GBR</span>
                            </td>
                            <td class="col-3"><span class="country">Great Britain</span></td>
                            <td class="col-4">27</td>
                            <td class="col-5">23</td>
                            <td class="col-6">17</td>
                            <td class="col-7"><strong>67</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="CHN" >
                            <td class="col-1">
                                <strong><strong>3</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="China" aria-label="China" class="flag sprite-flags sprite-flags--CHN"></span>
                                <span class="country">CHN</span>
                            </td>
                            <td class="col-3"><span class="country">China</span></td>
                            <td class="col-4">26</td>
                            <td class="col-5">18</td>
                            <td class="col-6">26</td>
                            <td class="col-7"><strong>70</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="RUS" >
                            <td class="col-1">
                                <strong><strong>4</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Russian Federation" aria-label="Russian Federation" class="flag sprite-flags sprite-flags--RUS"></span>
                                <span class="country">RUS</span>
                            </td>
                            <td class="col-3"><span class="country">Russian Federation</span></td>
                            <td class="col-4">19</td>
                            <td class="col-5">18</td>
                            <td class="col-6">19</td>
                            <td class="col-7"><strong>56</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="GER" >
                            <td class="col-1">
                                <strong><strong>5</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Germany" aria-label="Germany" class="flag sprite-flags sprite-flags--GER"></span>
                                <span class="country">GER</span>
                            </td>
                            <td class="col-3"><span class="country">Germany</span></td>
                            <td class="col-4">17</td>
                            <td class="col-5">10</td>
                            <td class="col-6">15</td>
                            <td class="col-7"><strong>42</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="JPN" >
                            <td class="col-1">
                                <strong><strong>6</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Japan" aria-label="Japan" class="flag sprite-flags sprite-flags--JPN"></span>
                                <span class="country">JPN</span>
                            </td>
                            <td class="col-3"><span class="country">Japan</span></td>
                            <td class="col-4">12</td>
                            <td class="col-5">8</td>
                            <td class="col-6">21</td>
                            <td class="col-7"><strong>41</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="FRA" >
                            <td class="col-1">
                                <strong><strong>7</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="France" aria-label="France" class="flag sprite-flags sprite-flags--FRA"></span>
                                <span class="country">FRA</span>
                            </td>
                            <td class="col-3"><span class="country">France</span></td>
                            <td class="col-4">10</td>
                            <td class="col-5">18</td>
                            <td class="col-6">14</td>
                            <td class="col-7"><strong>42</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="KOR" >
                            <td class="col-1">
                                <strong><strong>8</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Republic of Korea" aria-label="Republic of Korea" class="flag sprite-flags sprite-flags--KOR"></span>
                                <span class="country">KOR</span>
                            </td>
                            <td class="col-3"><span class="country">Republic of Korea</span></td>
                            <td class="col-4">9</td>
                            <td class="col-5">3</td>
                            <td class="col-6">9</td>
                            <td class="col-7"><strong>21</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ITA" >
                            <td class="col-1">
                                <strong><strong>9</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Italy" aria-label="Italy" class="flag sprite-flags sprite-flags--ITA"></span>
                                <span class="country">ITA</span>
                            </td>
                            <td class="col-3"><span class="country">Italy</span></td>
                            <td class="col-4">8</td>
                            <td class="col-5">12</td>
                            <td class="col-6">8</td>
                            <td class="col-7"><strong>28</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="AUS" >
                            <td class="col-1">
                                <strong><strong>10</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Australia" aria-label="Australia" class="flag sprite-flags sprite-flags--AUS"></span>
                                <span class="country">AUS</span>
                            </td>
                            <td class="col-3"><span class="country">Australia</span></td>
                            <td class="col-4">8</td>
                            <td class="col-5">11</td>
                            <td class="col-6">10</td>
                            <td class="col-7"><strong>29</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="NED" >
                            <td class="col-1">
                                <strong><strong>11</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Netherlands" aria-label="Netherlands" class="flag sprite-flags sprite-flags--NED"></span>
                                <span class="country">NED</span>
                            </td>
                            <td class="col-3"><span class="country">Netherlands</span></td>
                            <td class="col-4">8</td>
                            <td class="col-5">7</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>19</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="HUN" >
                            <td class="col-1">
                                <strong><strong>12</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Hungary" aria-label="Hungary" class="flag sprite-flags sprite-flags--HUN"></span>
                                <span class="country">HUN</span>
                            </td>
                            <td class="col-3"><span class="country">Hungary</span></td>
                            <td class="col-4">8</td>
                            <td class="col-5">3</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>15</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BRA" >
                            <td class="col-1">
                                <strong><strong>13</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Brazil" aria-label="Brazil" class="flag sprite-flags sprite-flags--BRA"></span>
                                <span class="country">BRA</span>
                            </td>
                            <td class="col-3"><span class="country">Brazil</span></td>
                            <td class="col-4">7</td>
                            <td class="col-5">6</td>
                            <td class="col-6">6</td>
                            <td class="col-7"><strong>19</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ESP" >
                            <td class="col-1">
                                <strong><strong>14</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Spain" aria-label="Spain" class="flag sprite-flags sprite-flags--ESP"></span>
                                <span class="country">ESP</span>
                            </td>
                            <td class="col-3"><span class="country">Spain</span></td>
                            <td class="col-4">7</td>
                            <td class="col-5">4</td>
                            <td class="col-6">6</td>
                            <td class="col-7"><strong>17</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="KEN" >
                            <td class="col-1">
                                <strong><strong>15</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Kenya" aria-label="Kenya" class="flag sprite-flags sprite-flags--KEN"></span>
                                <span class="country">KEN</span>
                            </td>
                            <td class="col-3"><span class="country">Kenya</span></td>
                            <td class="col-4">6</td>
                            <td class="col-5">6</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>13</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="JAM" >
                            <td class="col-1">
                                <strong><strong>16</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Jamaica" aria-label="Jamaica" class="flag sprite-flags sprite-flags--JAM"></span>
                                <span class="country">JAM</span>
                            </td>
                            <td class="col-3"><span class="country">Jamaica</span></td>
                            <td class="col-4">6</td>
                            <td class="col-5">3</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>11</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="CRO" >
                            <td class="col-1">
                                <strong><strong>17</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Croatia" aria-label="Croatia" class="flag sprite-flags sprite-flags--CRO"></span>
                                <span class="country">CRO</span>
                            </td>
                            <td class="col-3"><span class="country">Croatia</span></td>
                            <td class="col-4">5</td>
                            <td class="col-5">3</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>10</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="CUB" >
                            <td class="col-1">
                                <strong><strong>18</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Cuba" aria-label="Cuba" class="flag sprite-flags sprite-flags--CUB"></span>
                                <span class="country">CUB</span>
                            </td>
                            <td class="col-3"><span class="country">Cuba</span></td>
                            <td class="col-4">5</td>
                            <td class="col-5">2</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>11</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="NZL" >
                            <td class="col-1">
                                <strong><strong>19</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="New Zealand" aria-label="New Zealand" class="flag sprite-flags sprite-flags--NZL"></span>
                                <span class="country">NZL</span>
                            </td>
                            <td class="col-3"><span class="country">New Zealand</span></td>
                            <td class="col-4">4</td>
                            <td class="col-5">9</td>
                            <td class="col-6">5</td>
                            <td class="col-7"><strong>18</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="CAN" >
                            <td class="col-1">
                                <strong><strong>20</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Canada" aria-label="Canada" class="flag sprite-flags sprite-flags--CAN"></span>
                                <span class="country">CAN</span>
                            </td>
                            <td class="col-3"><span class="country">Canada</span></td>
                            <td class="col-4">4</td>
                            <td class="col-5">3</td>
                            <td class="col-6">15</td>
                            <td class="col-7"><strong>22</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="UZB" >
                            <td class="col-1">
                                <strong><strong>21</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Uzbekistan" aria-label="Uzbekistan" class="flag sprite-flags sprite-flags--UZB"></span>
                                <span class="country">UZB</span>
                            </td>
                            <td class="col-3"><span class="country">Uzbekistan</span></td>
                            <td class="col-4">4</td>
                            <td class="col-5">2</td>
                            <td class="col-6">7</td>
                            <td class="col-7"><strong>13</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="KAZ" >
                            <td class="col-1">
                                <strong><strong>22</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Kazakhstan" aria-label="Kazakhstan" class="flag sprite-flags sprite-flags--KAZ"></span>
                                <span class="country">KAZ</span>
                            </td>
                            <td class="col-3"><span class="country">Kazakhstan</span></td>
                            <td class="col-4">3</td>
                            <td class="col-5">5</td>
                            <td class="col-6">9</td>
                            <td class="col-7"><strong>17</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="COL" >
                            <td class="col-1">
                                <strong><strong>23</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Colombia" aria-label="Colombia" class="flag sprite-flags sprite-flags--COL"></span>
                                <span class="country">COL</span>
                            </td>
                            <td class="col-3"><span class="country">Colombia</span></td>
                            <td class="col-4">3</td>
                            <td class="col-5">2</td>
                            <td class="col-6">3</td>
                            <td class="col-7"><strong>8</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="SUI" >
                            <td class="col-1">
                                <strong><strong>24</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Switzerland" aria-label="Switzerland" class="flag sprite-flags sprite-flags--SUI"></span>
                                <span class="country">SUI</span>
                            </td>
                            <td class="col-3"><span class="country">Switzerland</span></td>
                            <td class="col-4">3</td>
                            <td class="col-5">2</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>7</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="IRI" >
                            <td class="col-1">
                                <strong><strong>25</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Iran" aria-label="Iran" class="flag sprite-flags sprite-flags--IRI"></span>
                                <span class="country">IRI</span>
                            </td>
                            <td class="col-3"><span class="country">Iran</span></td>
                            <td class="col-4">3</td>
                            <td class="col-5">1</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>8</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="GRE" >
                            <td class="col-1">
                                <strong><strong>26</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Greece" aria-label="Greece" class="flag sprite-flags sprite-flags--GRE"></span>
                                <span class="country">GRE</span>
                            </td>
                            <td class="col-3"><span class="country">Greece</span></td>
                            <td class="col-4">3</td>
                            <td class="col-5">1</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>6</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ARG" >
                            <td class="col-1">
                                <strong><strong>27</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Argentina" aria-label="Argentina" class="flag sprite-flags sprite-flags--ARG"></span>
                                <span class="country">ARG</span>
                            </td>
                            <td class="col-3"><span class="country">Argentina</span></td>
                            <td class="col-4">3</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>4</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="DEN" >
                            <td class="col-1">
                                <strong><strong>28</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Denmark" aria-label="Denmark" class="flag sprite-flags sprite-flags--DEN"></span>
                                <span class="country">DEN</span>
                            </td>
                            <td class="col-3"><span class="country">Denmark</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">6</td>
                            <td class="col-6">7</td>
                            <td class="col-7"><strong>15</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="SWE" >
                            <td class="col-1">
                                <strong><strong>29</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Sweden" aria-label="Sweden" class="flag sprite-flags sprite-flags--SWE"></span>
                                <span class="country">SWE</span>
                            </td>
                            <td class="col-3"><span class="country">Sweden</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">6</td>
                            <td class="col-6">3</td>
                            <td class="col-7"><strong>11</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="RSA" >
                            <td class="col-1">
                                <strong><strong>30</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="South Africa" aria-label="South Africa" class="flag sprite-flags sprite-flags--RSA"></span>
                                <span class="country">RSA</span>
                            </td>
                            <td class="col-3"><span class="country">South Africa</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">6</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>10</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="UKR" >
                            <td class="col-1">
                                <strong><strong>31</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Ukraine" aria-label="Ukraine" class="flag sprite-flags sprite-flags--UKR"></span>
                                <span class="country">UKR</span>
                            </td>
                            <td class="col-3"><span class="country">Ukraine</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">5</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>11</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="SRB" >
                            <td class="col-1">
                                <strong><strong>32</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Serbia" aria-label="Serbia" class="flag sprite-flags sprite-flags--SRB"></span>
                                <span class="country">SRB</span>
                            </td>
                            <td class="col-3"><span class="country">Serbia</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">4</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>8</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="POL" >
                            <td class="col-1">
                                <strong><strong>33</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Poland" aria-label="Poland" class="flag sprite-flags sprite-flags--POL"></span>
                                <span class="country">POL</span>
                            </td>
                            <td class="col-3"><span class="country">Poland</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">3</td>
                            <td class="col-6">6</td>
                            <td class="col-7"><strong>11</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="PRK" >
                            <td class="col-1">
                                <strong><strong>34</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="DPR Korea" aria-label="DPR Korea" class="flag sprite-flags sprite-flags--PRK"></span>
                                <span class="country">PRK</span>
                            </td>
                            <td class="col-3"><span class="country">DPR Korea</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">3</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>7</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BEL" >
                            <td class="col-1">
                                <strong><strong>35</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Belgium" aria-label="Belgium" class="flag sprite-flags sprite-flags--BEL"></span>
                                <span class="country">BEL</span>
                            </td>
                            <td class="col-3"><span class="country">Belgium</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">2</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>6</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="THA" >
                            <td class="col-1">
                                <strong><strong>35</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Thailand" aria-label="Thailand" class="flag sprite-flags sprite-flags--THA"></span>
                                <span class="country">THA</span>
                            </td>
                            <td class="col-3"><span class="country">Thailand</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">2</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>6</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="SVK" >
                            <td class="col-1">
                                <strong><strong>37</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Slovakia" aria-label="Slovakia" class="flag sprite-flags sprite-flags--SVK"></span>
                                <span class="country">SVK</span>
                            </td>
                            <td class="col-3"><span class="country">Slovakia</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">2</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>4</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="GEO" >
                            <td class="col-1">
                                <strong><strong>38</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Georgia" aria-label="Georgia" class="flag sprite-flags sprite-flags--GEO"></span>
                                <span class="country">GEO</span>
                            </td>
                            <td class="col-3"><span class="country">Georgia</span></td>
                            <td class="col-4">2</td>
                            <td class="col-5">1</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>7</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="AZE" >
                            <td class="col-1">
                                <strong><strong>39</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Azerbaijan" aria-label="Azerbaijan" class="flag sprite-flags sprite-flags--AZE"></span>
                                <span class="country">AZE</span>
                            </td>
                            <td class="col-3"><span class="country">Azerbaijan</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">7</td>
                            <td class="col-6">10</td>
                            <td class="col-7"><strong>18</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BLR" >
                            <td class="col-1">
                                <strong><strong>40</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Belarus" aria-label="Belarus" class="flag sprite-flags sprite-flags--BLR"></span>
                                <span class="country">BLR</span>
                            </td>
                            <td class="col-3"><span class="country">Belarus</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">4</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>9</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="TUR" >
                            <td class="col-1">
                                <strong><strong>41</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Turkey" aria-label="Turkey" class="flag sprite-flags sprite-flags--TUR"></span>
                                <span class="country">TUR</span>
                            </td>
                            <td class="col-3"><span class="country">Turkey</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">3</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>8</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ARM" >
                            <td class="col-1">
                                <strong><strong>42</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Armenia" aria-label="Armenia" class="flag sprite-flags sprite-flags--ARM"></span>
                                <span class="country">ARM</span>
                            </td>
                            <td class="col-3"><span class="country">Armenia</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">3</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>4</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="CZE" >
                            <td class="col-1">
                                <strong><strong>43</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Czech Republic" aria-label="Czech Republic" class="flag sprite-flags sprite-flags--CZE"></span>
                                <span class="country">CZE</span>
                            </td>
                            <td class="col-3"><span class="country">Czech Republic</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">2</td>
                            <td class="col-6">7</td>
                            <td class="col-7"><strong>10</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ETH" >
                            <td class="col-1">
                                <strong><strong>44</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Ethiopia" aria-label="Ethiopia" class="flag sprite-flags sprite-flags--ETH"></span>
                                <span class="country">ETH</span>
                            </td>
                            <td class="col-3"><span class="country">Ethiopia</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">2</td>
                            <td class="col-6">5</td>
                            <td class="col-7"><strong>8</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="SLO" >
                            <td class="col-1">
                                <strong><strong>45</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Slovenia" aria-label="Slovenia" class="flag sprite-flags sprite-flags--SLO"></span>
                                <span class="country">SLO</span>
                            </td>
                            <td class="col-3"><span class="country">Slovenia</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">2</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>4</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="INA" >
                            <td class="col-1">
                                <strong><strong>46</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Indonesia" aria-label="Indonesia" class="flag sprite-flags sprite-flags--INA"></span>
                                <span class="country">INA</span>
                            </td>
                            <td class="col-3"><span class="country">Indonesia</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">2</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>3</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ROU" >
                            <td class="col-1">
                                <strong><strong>47</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Romania" aria-label="Romania" class="flag sprite-flags sprite-flags--ROU"></span>
                                <span class="country">ROU</span>
                            </td>
                            <td class="col-3"><span class="country">Romania</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">1</td>
                            <td class="col-6">3</td>
                            <td class="col-7"><strong>5</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BRN" >
                            <td class="col-1">
                                <strong><strong>48</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Bahrain" aria-label="Bahrain" class="flag sprite-flags sprite-flags--BRN"></span>
                                <span class="country">BRN</span>
                            </td>
                            <td class="col-3"><span class="country">Bahrain</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="VIE" >
                            <td class="col-1">
                                <strong><strong>48</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Vietnam" aria-label="Vietnam" class="flag sprite-flags sprite-flags--VIE"></span>
                                <span class="country">VIE</span>
                            </td>
                            <td class="col-3"><span class="country">Vietnam</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="TPE" >
                            <td class="col-1">
                                <strong><strong>50</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Taipei (Chinese Taipei)" aria-label="Taipei (Chinese Taipei)" class="flag sprite-flags sprite-flags--TPE"></span>
                                <span class="country">TPE</span>
                            </td>
                            <td class="col-3"><span class="country">Taipei (Chinese Taipei)</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>3</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BAH" >
                            <td class="col-1">
                                <strong><strong>51</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Bahamas" aria-label="Bahamas" class="flag sprite-flags sprite-flags--BAH"></span>
                                <span class="country">BAH</span>
                            </td>
                            <td class="col-3"><span class="country">Bahamas</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="CIV" >
                            <td class="col-1">
                                <strong><strong>51</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="C&#244;te d&#39;Ivoire" aria-label="C&#244;te d&#39;Ivoire" class="flag sprite-flags sprite-flags--CIV"></span>
                                <span class="country">CIV</span>
                            </td>
                            <td class="col-3"><span class="country">C&#244;te d&#39;Ivoire</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="IOA" >
                            <td class="col-1">
                                <strong><strong>51</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="IOA" aria-label="IOA" class="flag sprite-flags sprite-flags--IOA"></span>
                                <span class="country">IOA</span>
                            </td>
                            <td class="col-3"><span class="country">IOA</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="FIJ" >
                            <td class="col-1">
                                <strong><strong>54</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Fiji" aria-label="Fiji" class="flag sprite-flags sprite-flags--FIJ"></span>
                                <span class="country">FIJ</span>
                            </td>
                            <td class="col-3"><span class="country">Fiji</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="JOR" >
                            <td class="col-1">
                                <strong><strong>54</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Jordan" aria-label="Jordan" class="flag sprite-flags sprite-flags--JOR"></span>
                                <span class="country">JOR</span>
                            </td>
                            <td class="col-3"><span class="country">Jordan</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="KOS" >
                            <td class="col-1">
                                <strong><strong>54</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Kosovo" aria-label="Kosovo" class="flag sprite-flags sprite-flags--KOS"></span>
                                <span class="country">KOS</span>
                            </td>
                            <td class="col-3"><span class="country">Kosovo</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="PUR" >
                            <td class="col-1">
                                <strong><strong>54</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Puerto Rico" aria-label="Puerto Rico" class="flag sprite-flags sprite-flags--PUR"></span>
                                <span class="country">PUR</span>
                            </td>
                            <td class="col-3"><span class="country">Puerto Rico</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="SIN" >
                            <td class="col-1">
                                <strong><strong>54</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Singapore" aria-label="Singapore" class="flag sprite-flags sprite-flags--SIN"></span>
                                <span class="country">SIN</span>
                            </td>
                            <td class="col-3"><span class="country">Singapore</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="TJK" >
                            <td class="col-1">
                                <strong><strong>54</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Tajikistan" aria-label="Tajikistan" class="flag sprite-flags sprite-flags--TJK"></span>
                                <span class="country">TJK</span>
                            </td>
                            <td class="col-3"><span class="country">Tajikistan</span></td>
                            <td class="col-4">1</td>
                            <td class="col-5">0</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="MAS" >
                            <td class="col-1">
                                <strong><strong>60</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Malaysia" aria-label="Malaysia" class="flag sprite-flags sprite-flags--MAS"></span>
                                <span class="country">MAS</span>
                            </td>
                            <td class="col-3"><span class="country">Malaysia</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">4</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>5</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="MEX" >
                            <td class="col-1">
                                <strong><strong>61</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Mexico" aria-label="Mexico" class="flag sprite-flags sprite-flags--MEX"></span>
                                <span class="country">MEX</span>
                            </td>
                            <td class="col-3"><span class="country">Mexico</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">3</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>5</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ALG" >
                            <td class="col-1">
                                <strong><strong>62</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Algeria" aria-label="Algeria" class="flag sprite-flags sprite-flags--ALG"></span>
                                <span class="country">ALG</span>
                            </td>
                            <td class="col-3"><span class="country">Algeria</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">2</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="IRL" >
                            <td class="col-1">
                                <strong><strong>62</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Ireland" aria-label="Ireland" class="flag sprite-flags sprite-flags--IRL"></span>
                                <span class="country">IRL</span>
                            </td>
                            <td class="col-3"><span class="country">Ireland</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">2</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="LTU" >
                            <td class="col-1">
                                <strong><strong>64</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Lithuania" aria-label="Lithuania" class="flag sprite-flags sprite-flags--LTU"></span>
                                <span class="country">LTU</span>
                            </td>
                            <td class="col-3"><span class="country">Lithuania</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">3</td>
                            <td class="col-7"><strong>4</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BUL" >
                            <td class="col-1">
                                <strong><strong>65</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Bulgaria" aria-label="Bulgaria" class="flag sprite-flags sprite-flags--BUL"></span>
                                <span class="country">BUL</span>
                            </td>
                            <td class="col-3"><span class="country">Bulgaria</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>3</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="VEN" >
                            <td class="col-1">
                                <strong><strong>65</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Venezuela" aria-label="Venezuela" class="flag sprite-flags sprite-flags--VEN"></span>
                                <span class="country">VEN</span>
                            </td>
                            <td class="col-3"><span class="country">Venezuela</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>3</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="IND" >
                            <td class="col-1">
                                <strong><strong>67</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="India" aria-label="India" class="flag sprite-flags sprite-flags--IND"></span>
                                <span class="country">IND</span>
                            </td>
                            <td class="col-3"><span class="country">India</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="MGL" >
                            <td class="col-1">
                                <strong><strong>67</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Mongolia" aria-label="Mongolia" class="flag sprite-flags sprite-flags--MGL"></span>
                                <span class="country">MGL</span>
                            </td>
                            <td class="col-3"><span class="country">Mongolia</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="BDI" >
                            <td class="col-1">
                                <strong><strong>69</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Burundi" aria-label="Burundi" class="flag sprite-flags sprite-flags--BDI"></span>
                                <span class="country">BDI</span>
                            </td>
                            <td class="col-3"><span class="country">Burundi</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="GRN" >
                            <td class="col-1">
                                <strong><strong>69</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Grenada" aria-label="Grenada" class="flag sprite-flags sprite-flags--GRN"></span>
                                <span class="country">GRN</span>
                            </td>
                            <td class="col-3"><span class="country">Grenada</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="NIG" >
                            <td class="col-1">
                                <strong><strong>69</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Niger" aria-label="Niger" class="flag sprite-flags sprite-flags--NIG"></span>
                                <span class="country">NIG</span>
                            </td>
                            <td class="col-3"><span class="country">Niger</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="PHI" >
                            <td class="col-1">
                                <strong><strong>69</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Philippines" aria-label="Philippines" class="flag sprite-flags sprite-flags--PHI"></span>
                                <span class="country">PHI</span>
                            </td>
                            <td class="col-3"><span class="country">Philippines</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="QAT" >
                            <td class="col-1">
                                <strong><strong>69</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Qatar" aria-label="Qatar" class="flag sprite-flags sprite-flags--QAT"></span>
                                <span class="country">QAT</span>
                            </td>
                            <td class="col-3"><span class="country">Qatar</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">1</td>
                            <td class="col-6">0</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="NOR" >
                            <td class="col-1">
                                <strong><strong>74</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Norway" aria-label="Norway" class="flag sprite-flags sprite-flags--NOR"></span>
                                <span class="country">NOR</span>
                            </td>
                            <td class="col-3"><span class="country">Norway</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">4</td>
                            <td class="col-7"><strong>4</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="EGY" >
                            <td class="col-1">
                                <strong><strong>75</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Egypt" aria-label="Egypt" class="flag sprite-flags sprite-flags--EGY"></span>
                                <span class="country">EGY</span>
                            </td>
                            <td class="col-3"><span class="country">Egypt</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">3</td>
                            <td class="col-7"><strong>3</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="TUN" >
                            <td class="col-1">
                                <strong><strong>75</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Tunisia" aria-label="Tunisia" class="flag sprite-flags sprite-flags--TUN"></span>
                                <span class="country">TUN</span>
                            </td>
                            <td class="col-3"><span class="country">Tunisia</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">3</td>
                            <td class="col-7"><strong>3</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="ISR" >
                            <td class="col-1">
                                <strong><strong>77</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Israel" aria-label="Israel" class="flag sprite-flags sprite-flags--ISR"></span>
                                <span class="country">ISR</span>
                            </td>
                            <td class="col-3"><span class="country">Israel</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">2</td>
                            <td class="col-7"><strong>2</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="AUT" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Austria" aria-label="Austria" class="flag sprite-flags sprite-flags--AUT"></span>
                                <span class="country">AUT</span>
                            </td>
                            <td class="col-3"><span class="country">Austria</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="DOM" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Dominican Republic" aria-label="Dominican Republic" class="flag sprite-flags sprite-flags--DOM"></span>
                                <span class="country">DOM</span>
                            </td>
                            <td class="col-3"><span class="country">Dominican Republic</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="EST" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Estonia" aria-label="Estonia" class="flag sprite-flags sprite-flags--EST"></span>
                                <span class="country">EST</span>
                            </td>
                            <td class="col-3"><span class="country">Estonia</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="FIN" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Finland" aria-label="Finland" class="flag sprite-flags sprite-flags--FIN"></span>
                                <span class="country">FIN</span>
                            </td>
                            <td class="col-3"><span class="country">Finland</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="MAR" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Morocco" aria-label="Morocco" class="flag sprite-flags sprite-flags--MAR"></span>
                                <span class="country">MAR</span>
                            </td>
                            <td class="col-3"><span class="country">Morocco</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="MDA" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Republic of Moldova" aria-label="Republic of Moldova" class="flag sprite-flags sprite-flags--MDA"></span>
                                <span class="country">MDA</span>
                            </td>
                            <td class="col-3"><span class="country">Republic of Moldova</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="NGR" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Nigeria" aria-label="Nigeria" class="flag sprite-flags sprite-flags--NGR"></span>
                                <span class="country">NGR</span>
                            </td>
                            <td class="col-3"><span class="country">Nigeria</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="POR" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Portugal" aria-label="Portugal" class="flag sprite-flags sprite-flags--POR"></span>
                                <span class="country">POR</span>
                            </td>
                            <td class="col-3"><span class="country">Portugal</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="TTO" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="Trinidad and Tobago" aria-label="Trinidad and Tobago" class="flag sprite-flags sprite-flags--TTO"></span>
                                <span class="country">TTO</span>
                            </td>
                            <td class="col-3"><span class="country">Trinidad and Tobago</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
                        <tr tabindex="0" data-href="" role="" class="table-medal-countries__link-table" data-odfcode="UAE" >
                            <td class="col-1">
                                <strong><strong>78</strong></strong>
                            </td>
                            <td class="col-2">
                                <span title="United Arab Emirates" aria-label="United Arab Emirates" class="flag sprite-flags sprite-flags--UAE"></span>
                                <span class="country">UAE</span>
                            </td>
                            <td class="col-3"><span class="country">United Arab Emirates</span></td>
                            <td class="col-4">0</td>
                            <td class="col-5">0</td>
                            <td class="col-6">1</td>
                            <td class="col-7"><strong>1</strong></td>
                        </tr>
                        
			</tbody>
		</table>            </div>
        </small>
    </jsp:attribute> 
</idisc:page_without_slider>

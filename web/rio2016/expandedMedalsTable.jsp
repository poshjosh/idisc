<%-- 
    Document   : rio2016medalsTable
    Created on : Aug 11, 2016, 11:47:46 PM
    Author     : Josh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/idisc" prefix="idisc"%>

<idisc:page_with_slider   
    pageTitle="Rio 2016 Medals Table - ${appName}" 
    pageKeywords="rio2016,medals table" 
    pageDescription="Rio 2016 Medals Table">
    <jsp:attribute name="pageHeadInclude" trim="true">
        <style type="text/css">
            .medals{ background-color:turquoise; }
            .table-medal-countries__link-table{ background-color:aqua; }
            .table-expand{visibility: visible; }
        </style>    
        <script type="text/javascript">
            function toggleVisibility() {
                var tags = document.getElementsByClassName('table-expand');
                var total = tags.length;
                var newtext;
                for(var i=0; i<total; i++) {
                    var newvis;
                    var tag = tags[i];
                    if(tag.style.visibility === 'collapse') {
                        newvis = 'visible';
                        newtext = 'Less';
                    }else{
                        newvis = 'collapse';
                        newtext = 'More';
                    }
                    tag.style.visibility = newvis;
                }
                var btn = document.getElementById('expandTableButton');
                btn.value = newtext;
                btn.innerHTML = newtext;
            }
        </script>    
    </jsp:attribute>    
    <jsp:attribute name="pageContent" trim="true">
        <button id="expandTableButton" onclick="toggleVisibility(); return true;" style="padding:0.5em;">Less</button>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-rifle-women"> 10m Air Rifle Women</a></td>
                <td class="col-4">THRASHER Virginia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-400m-freestyle"> Women&#39;s 400m Freestyle</a></td>
                <td class="col-4">LEDECKY Katie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-100m-freestyle-relay"> Men&#39;s 4 x 100m Freestyle Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-backstroke"> Men&#39;s 100m Backstroke</a></td>
                <td class="col-4">MURPHY Ryan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-breaststroke"> Women&#39;s 100m Breaststroke</a></td>
                <td class="col-4">KING Lilly</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-freestyle"> Women&#39;s 200m Freestyle</a></td>
                <td class="col-4">LEDECKY Katie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-butterfly"> Men&#39;s 200m Butterfly</a></td>
                <td class="col-4">PHELPS Michael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-200m-freestyle-relay"> Men&#39;s 4 x 200m Freestyle Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-womens-individual-time-trial"> Women&#39;s Individual Time Trial</a></td>
                <td class="col-4">ARMSTRONG Kristin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-200m-freestyle-relay"> Women&#39;s 4 x 200m Freestyle Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg"> Women -78 kg</a></td>
                <td class="col-4">HARRISON Kayla</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-individual-all-around"> Women&#39;s Individual All-Around</a></td>
                <td class="col-4">BILES Simone</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-backstroke"> Men&#39;s 200m Backstroke</a></td>
                <td class="col-4">MURPHY Ryan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-individual-medley"> Men&#39;s 200m Individual Medley</a></td>
                <td class="col-4">PHELPS Michael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-freestyle"> Women&#39;s 100m Freestyle</a></td>
                <td class="col-4">MANUEL Simone</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-backstroke"> Women&#39;s 200m Backstroke</a></td>
                <td class="col-4">DIRADO Madeline</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-800m-freestyle"> Women&#39;s 800m Freestyle</a></td>
                <td class="col-4">LEDECKY Katie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-50m-freestyle"> Men&#39;s 50m Freestyle</a></td>
                <td class="col-4">ERVIN Anthony</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-shot-put"> Women&#39;s Shot Put</a></td>
                <td class="col-4">CARTER Michelle</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-eight"> Women&#39;s Eight</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-long-jump"> Men&#39;s Long Jump</a></td>
                <td class="col-4">HENDERSON Jeff</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-100m-medley-relay"> Women&#39;s 4 x 100m Medley Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-100m-medley-relay"> Men&#39;s 4 x 100m Medley Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-vault"> Women&#39;s Vault</a></td>
                <td class="col-4">BILES Simone</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mixed-doubles"> Mixed Doubles</a></td>
                <td class="col-4">Sock / Mattek-Sands</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-triple-jump"> Men&#39;s Triple Jump</a></td>
                <td class="col-4">TAYLOR Christian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-floor-exercise"> Women&#39;s Floor Exercise</a></td>
                <td class="col-4">BILES Simone</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-long-jump"> Women&#39;s Long Jump</a></td>
                <td class="col-4">BARTOLETTA Tianna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-100m-hurdles"> Women&#39;s 100m Hurdles</a></td>
                <td class="col-4">ROLLINS Brianna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-400m-hurdles"> Men&#39;s 400m Hurdles</a></td>
                <td class="col-4">CLEMENT Kerron</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-53-kg"> Women&#39;s Freestyle 53 kg</a></td>
                <td class="col-4">MAROULIS Helen Louise</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-shot-put"> Men&#39;s Shot Put</a></td>
                <td class="col-4">CROUSER Ryan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-decathlon"> Men&#39;s Decathlon</a></td>
                <td class="col-4">EATON Ashton</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-400m-hurdles"> Women&#39;s 400m Hurdles</a></td>
                <td class="col-4">MUHAMMAD Dalilah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling BMX</strong></td>
                <td class="col-3"><a href="/en/cycling-bmx-standings-cb-men"> Men</a></td>
                <td class="col-4">FIELDS Connor</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Water Polo</strong></td>
                <td class="col-3"><a href="/en/water-polo-standings-wp-women"> Women</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-4-x-100m-relay"> Women&#39;s 4 x 100m Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Triathlon</strong></td>
                <td class="col-3"><a href="/en/triathlon-standings-tr-women"> Women</a></td>
                <td class="col-4">JORGENSEN Gwen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Basketball</strong></td>
                <td class="col-3"><a href="/en/basketball-standings-bk-women"> Women</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-1500m"> Men&#39;s 1500m</a></td>
                <td class="col-4">CENTROWITZ Matthew</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-4-x-400m-relay"> Women&#39;s 4 x 400m Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-4-x-400m-relay"> Men&#39;s 4 x 400m Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-middle-69-75kg"> Women&#39;s Middle (69-75kg)</a></td>
                <td class="col-4">SHIELDS Claressa Maria</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-97-kg"> Men&#39;s Freestyle 97 kg</a></td>
                <td class="col-4">SNYDER Kyle Frederick</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Basketball</strong></td>
                <td class="col-3"><a href="/en/basketball-standings-bk-men"> Men</a></td>
                <td class="col-4">United States</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-400m-individual-medley"> Men&#39;s 400m Individual Medley</a></td>
                <td class="col-4">KALISZ Chase</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-400m-individual-medley"> Women&#39;s 400m Individual Medley</a></td>
                <td class="col-4">DIRADO Madeline</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-100m-freestyle-relay"> Women&#39;s 4 x 100m Freestyle Relay</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-foil-individual"> Men&#39;s Foil Individual</a></td>
                <td class="col-4">MASSIALAS Alexander</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-synchronised-10m-platform"> Men&#39;s Synchronised 10m Platform</a></td>
                <td class="col-4">Boudia / Johnson</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-backstroke"> Women&#39;s 100m Backstroke</a></td>
                <td class="col-4">BAKER Kathleen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-81-kg"> Men -81 kg</a></td>
                <td class="col-4">STEVENS Travis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-synchronised-3m-springboard"> Men&#39;s Synchronised 3m Springboard</a></td>
                <td class="col-4">Dorman / Hixon</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-sabre-individual"> Men&#39;s Sabre Individual</a></td>
                <td class="col-4">HOMER Daryl</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-breaststroke"> Men&#39;s 200m Breaststroke</a></td>
                <td class="col-4">PRENOT Josh</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-individual-all-around"> Women&#39;s Individual All-Around</a></td>
                <td class="col-4">RAISMAN Alexandra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-butterfly"> Men&#39;s 100m Butterfly</a></td>
                <td class="col-4">PHELPS Michael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-single-sculls"> Women&#39;s Single Sculls</a></td>
                <td class="col-4">STONE Genevra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-team-pursuit"> Women&#39;s Team Pursuit</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-50m-freestyle"> Women&#39;s 50m Freestyle</a></td>
                <td class="col-4">MANUEL Simone</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-1500m-freestyle"> Men&#39;s 1500m Freestyle</a></td>
                <td class="col-4">JAEGER Connor</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-100m"> Women&#39;s 100m</a></td>
                <td class="col-4">BOWIE Tori</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mixed-doubles"> Mixed Doubles</a></td>
                <td class="col-4">Williams / Ram</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-uneven-bars"> Women&#39;s Uneven Bars</a></td>
                <td class="col-4">KOCIAN Madison</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-100m"> Men&#39;s 100m</a></td>
                <td class="col-4">GATLIN Justin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-balance-beam"> Women&#39;s Balance Beam</a></td>
                <td class="col-4">HERNANDEZ Lauren</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-400m"> Women&#39;s 400m</a></td>
                <td class="col-4">FELIX Allyson</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-triple-jump"> Men&#39;s Triple Jump</a></td>
                <td class="col-4">CLAYE Will</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-parallel-bars"> Men&#39;s Parallel Bars</a></td>
                <td class="col-4">LEYVA Danell</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-floor-exercise"> Women&#39;s Floor Exercise</a></td>
                <td class="col-4">RAISMAN Alexandra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-horizontal-bar"> Men&#39;s Horizontal Bar</a></td>
                <td class="col-4">LEYVA Danell</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-omnium"> Women&#39;s Omnium</a></td>
                <td class="col-4">HAMMER Sarah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-jumping-team"> Jumping Team</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-3000m-steeplechase"> Men&#39;s 3000m Steeplechase</a></td>
                <td class="col-4">JAGER Evan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-long-jump"> Women&#39;s Long Jump</a></td>
                <td class="col-4">REESE Brittney</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-100m-hurdles"> Women&#39;s 100m Hurdles</a></td>
                <td class="col-4">ALI Nia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-shot-put"> Men&#39;s Shot Put</a></td>
                <td class="col-4">KOVACS Joe</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling BMX</strong></td>
                <td class="col-3"><a href="/en/cycling-bmx-standings-cb-women"> Women</a></td>
                <td class="col-4">POST Alise</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-pole-vault"> Women&#39;s Pole Vault</a></td>
                <td class="col-4">MORRIS Sandi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-bantam-56kg"> Men&#39;s Bantam (56kg)</a></td>
                <td class="col-4">STEVENSON Shakur</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-5000m"> Men&#39;s 5000m</a></td>
                <td class="col-4">CHELIMO Paul Kipkemoi</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-trap-women"> Trap Women</a></td>
                <td class="col-4">COGDELL Corey</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-butterfly"> Women&#39;s 100m Butterfly</a></td>
                <td class="col-4">VOLLMER Dana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-breaststroke"> Men&#39;s 100m Breaststroke</a></td>
                <td class="col-4">MILLER Cody</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-400m-freestyle"> Women&#39;s 400m Freestyle</a></td>
                <td class="col-4">SMITH Leah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-freestyle"> Men&#39;s 200m Freestyle</a></td>
                <td class="col-4">DWYER Conor</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-backstroke"> Men&#39;s 100m Backstroke</a></td>
                <td class="col-4">PLUMMER David</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-breaststroke"> Women&#39;s 100m Breaststroke</a></td>
                <td class="col-4">MEILI Catherine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-eventing-individual"> Eventing Individual</a></td>
                <td class="col-4">DUTTON Phillip</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-individual-medley"> Women&#39;s 200m Individual Medley</a></td>
                <td class="col-4">DIRADO Madeline</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-freestyle"> Men&#39;s 100m Freestyle</a></td>
                <td class="col-4">ADRIAN Nathan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-skeet-women"> Skeet Women</a></td>
                <td class="col-4">RHODE Kimberly</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-mens-individual"> Men&#39;s Individual</a></td>
                <td class="col-4">ELLISON Brady</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-foil-team"> Men&#39;s Foil Team</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mens-doubles"> Men&#39;s Doubles</a></td>
                <td class="col-4">Sock / Johnson</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-50m-freestyle"> Men&#39;s 50m Freestyle</a></td>
                <td class="col-4">ADRIAN Nathan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-dressage-team"> Dressage Team</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-sabre-team"> Women&#39;s Sabre Team</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-fly-46-49kg"> Men&#39;s Light Fly (46-49kg)</a></td>
                <td class="col-4">HERNANDEZ Nico Miguel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Golf</strong></td>
                <td class="col-3"><a href="/en/golf-standings-go-mens-individual-stroke-play"> Men&#39;s Individual Stroke Play</a></td>
                <td class="col-4">KUCHAR Matt</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-pommel-horse"> Men&#39;s Pommel Horse</a></td>
                <td class="col-4">NADDOUR Alexander</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-75kg"> Women&#39;s +75kg</a></td>
                <td class="col-4">ROBLES Sarah Elizabeth</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-400m"> Men&#39;s 400m</a></td>
                <td class="col-4">MERRITT Lashawn</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-3000m-steeplechase"> Women&#39;s 3000m Steeplechase</a></td>
                <td class="col-4">COBURN Emma</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-balance-beam"> Women&#39;s Balance Beam</a></td>
                <td class="col-4">BILES Simone</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-800m"> Men&#39;s 800m</a></td>
                <td class="col-4">MURPHY Clayton</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-pole-vault"> Men&#39;s Pole Vault</a></td>
                <td class="col-4">KENDRICKS Sam</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-finn-men"> Finn Men</a></td>
                <td class="col-4">PAINE Caleb</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-1500m"> Women&#39;s 1500m</a></td>
                <td class="col-4">SIMPSON Jennifer</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-200m"> Women&#39;s 200m</a></td>
                <td class="col-4">BOWIE Tori</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-100m-hurdles"> Women&#39;s 100m Hurdles</a></td>
                <td class="col-4">CASTLIN Kristi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Beach Volleyball</strong></td>
                <td class="col-3"><a href="/en/beach-volleyball-standings-bv-women"> Women</a></td>
                <td class="col-4">Ross / Walsh Jennings</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-400m-hurdles"> Women&#39;s 400m Hurdles</a></td>
                <td class="col-4">SPENCER Ashley</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-86-kg"> Men&#39;s Freestyle 86 kg</a></td>
                <td class="col-4">COX J&#39;den Michael Tbory</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-10m-platform"> Men&#39;s 10m Platform</a></td>
                <td class="col-4">BOUDIA David</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg-2"> Women +67kg</a></td>
                <td class="col-4">GALLOWAY Jackie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Volleyball</strong></td>
                <td class="col-3"><a href="/en/volleyball-standings-vo-women"> Women</a></td>
                <td class="col-4">United States</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-marathon"> Men&#39;s Marathon</a></td>
                <td class="col-4">RUPP Galen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Volleyball</strong></td>
                <td class="col-3"><a href="/en/volleyball-standings-vo-men"> Men</a></td>
                <td class="col-4">United States</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-breaststroke"> Men&#39;s 100m Breaststroke</a></td>
                <td class="col-4">PEATY Adam</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-kayak-k1-men"> Kayak (K1) Men</a></td>
                <td class="col-4">CLARKE Joseph</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-synchronised-3m-springboard"> Men&#39;s Synchronised 3m Springboard</a></td>
                <td class="col-4">Laugher / Mears</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-team-sprint"> Men&#39;s Team Sprint</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-pair"> Women&#39;s Pair</a></td>
                <td class="col-4">Glover / Stanning</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-four"> Men&#39;s Four</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-team-pursuit"> Men&#39;s Team Pursuit</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-eight"> Men&#39;s Eight</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-team-pursuit"> Women&#39;s Team Pursuit</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-10-000m"> Men&#39;s 10,000m</a></td>
                <td class="col-4">FARAH Mohamed</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-floor-exercise"> Men&#39;s Floor Exercise</a></td>
                <td class="col-4">WHITLOCK Max</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Golf</strong></td>
                <td class="col-3"><a href="/en/golf-standings-go-mens-individual-stroke-play"> Men&#39;s Individual Stroke Play</a></td>
                <td class="col-4">ROSE Justin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-pommel-horse"> Men&#39;s Pommel Horse</a></td>
                <td class="col-4">WHITLOCK Max</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-sprint"> Men&#39;s Sprint</a></td>
                <td class="col-4">KENNY Jason</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">MURRAY Andy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-dressage-individual"> Dressage Individual</a></td>
                <td class="col-4">DUJARDIN Charlotte</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-finn-men"> Finn Men</a></td>
                <td class="col-4">SCOTT Giles</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-omnium"> Women&#39;s Omnium</a></td>
                <td class="col-4">TROTT Laura</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-keirin"> Men&#39;s Keirin</a></td>
                <td class="col-4">KENNY Jason</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Triathlon</strong></td>
                <td class="col-3"><a href="/en/triathlon-standings-tr-men"> Men</a></td>
                <td class="col-4">BROWNLEE Alistair</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-470-women"> 470 Women</a></td>
                <td class="col-4">Clark / Mills</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-57kg"> Women -57kg</a></td>
                <td class="col-4">JONES Jade</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-jumping-individual"> Jumping Individual</a></td>
                <td class="col-4">SKELTON Nick</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Hockey</strong></td>
                <td class="col-3"><a href="/en/hockey-standings-ho-women"> Women</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-200m"> Men&#39;s Kayak Single 200m</a></td>
                <td class="col-4">HEATH Liam</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-fly-48-51kg"> Women&#39;s Fly (48-51kg)</a></td>
                <td class="col-4">ADAMS Nicola</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-5000m"> Men&#39;s 5000m</a></td>
                <td class="col-4">FARAH Mohamed</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-400m-freestyle"> Women&#39;s 400m Freestyle</a></td>
                <td class="col-4">CARLIN Jazz</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-individual-medley"> Women&#39;s 200m Individual Medley</a></td>
                <td class="col-4">O&#39;CONNOR Siobhan-Marie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-200m-freestyle-relay"> Men&#39;s 4 x 200m Freestyle Relay</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-double-sculls"> Women&#39;s Double Sculls</a></td>
                <td class="col-4">Grainger / Thornley</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-canoe-double-c2-men"> Canoe Double (C2) Men</a></td>
                <td class="col-4">Florence / Hounslow</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rugby Sevens</strong></td>
                <td class="col-3"><a href="/en/rugby-sevens-standings-ru-men"> Men</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Trampoline Gymnastics</strong></td>
                <td class="col-3"><a href="/en/trampoline-gymnastics-standings-gt-women"> Women</a></td>
                <td class="col-4">PAGE Bryony</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-800m-freestyle"> Women&#39;s 800m Freestyle</a></td>
                <td class="col-4">CARLIN Jazz</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-dressage-team"> Dressage Team</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-eight"> Women&#39;s Eight</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-keirin"> Women&#39;s Keirin</a></td>
                <td class="col-4">JAMES Rebecca</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-100m-medley-relay"> Men&#39;s 4 x 100m Medley Relay</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-heptathlon"> Women&#39;s Heptathlon</a></td>
                <td class="col-4">ENNIS-HILL Jessica</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-rs-x-men"> RS:X Men</a></td>
                <td class="col-4">DEMPSEY Nick</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-pommel-horse"> Men&#39;s Pommel Horse</a></td>
                <td class="col-4">SMITH Louis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-sprint"> Men&#39;s Sprint</a></td>
                <td class="col-4">SKINNER Callum</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-omnium"> Men&#39;s Omnium</a></td>
                <td class="col-4">CAVENDISH Mark</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-sprint"> Women&#39;s Sprint</a></td>
                <td class="col-4">JAMES Rebecca</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-3m-springboard"> Men&#39;s 3m Springboard</a></td>
                <td class="col-4">LAUGHER Jack</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-double-200m"> Men&#39;s Kayak Double 200m</a></td>
                <td class="col-4">Heath / Schofield</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Triathlon</strong></td>
                <td class="col-3"><a href="/en/triathlon-standings-tr-men"> Men</a></td>
                <td class="col-4">BROWNLEE Jonathan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg"> Men -80kg</a></td>
                <td class="col-4">MUHAMMAD Lutalo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-super-heavy-91kg"> Men&#39;s Super Heavy (+91kg)</a></td>
                <td class="col-4">JOYCE Joe</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-trap-men"> Trap Men</a></td>
                <td class="col-4">LING Edward</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-synchronised-10m-platform"> Men&#39;s Synchronised 10m Platform</a></td>
                <td class="col-4">Daley / Goodfellow</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-mens-individual-time-trial"> Men&#39;s Individual Time Trial</a></td>
                <td class="col-4">FROOME Christopher</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-double-trap-men"> Double Trap Men</a></td>
                <td class="col-4">SCOTT Steven</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-70-kg"> Women -70 kg</a></td>
                <td class="col-4">CONWAY Sally</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-individual-all-around"> Men&#39;s Individual All-Around</a></td>
                <td class="col-4">WHITLOCK Max</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-long-jump"> Men&#39;s Long Jump</a></td>
                <td class="col-4">RUTHERFORD Greg</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-hammer-throw"> Women&#39;s Hammer Throw</a></td>
                <td class="col-4">HITCHON Sophie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-floor-exercise"> Women&#39;s Floor Exercise</a></td>
                <td class="col-4">TINKLER Amy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-horizontal-bar"> Men&#39;s Horizontal Bar</a></td>
                <td class="col-4">WILSON Nile</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-sprint"> Women&#39;s Sprint</a></td>
                <td class="col-4">MARCHANT Katy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-heavy-81kg"> Men&#39;s Light Heavy (81kg)</a></td>
                <td class="col-4">BUATSI Joshua</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mens-doubles"> Men&#39;s Doubles</a></td>
                <td class="col-4">Ellis / Langridge</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-4-x-100m-relay"> Women&#39;s 4 x 100m Relay</a></td>
                <td class="col-4">Great Britain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Triathlon</strong></td>
                <td class="col-3"><a href="/en/triathlon-standings-tr-women"> Women</a></td>
                <td class="col-4">HOLLAND Vicky</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg-2"> Women +67kg</a></td>
                <td class="col-4">WALKDEN Bianca</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-4-x-400m-relay"> Women&#39;s 4 x 400m Relay</a></td>
                <td class="col-4">Great Britain</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-pistol-women"> 10m Air Pistol Women</a></td>
                <td class="col-4">ZHANG Mengxue</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-synchronised-3m-springboard"> Women&#39;s Synchronised 3m Springboard</a></td>
                <td class="col-4">Wu / Shi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-56kg"> Men&#39;s 56kg</a></td>
                <td class="col-4">LONG Qingquan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-synchronised-10m-platform"> Men&#39;s Synchronised 10m Platform</a></td>
                <td class="col-4">Lin / Chen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-freestyle"> Men&#39;s 200m Freestyle</a></td>
                <td class="col-4">SUN Yang</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-63kg"> Women&#39;s 63kg</a></td>
                <td class="col-4">DENG Wei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-synchronised-10m-platform"> Women&#39;s Synchronised 10m Platform</a></td>
                <td class="col-4">Liu / Chen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-69kg"> Women&#39;s 69kg</a></td>
                <td class="col-4">XIANG Yanmei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">DING Ning</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">MA Long</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-20km-walk"> Men&#39;s 20km Walk</a></td>
                <td class="col-4">WANG Zhen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-team-sprint"> Women&#39;s Team Sprint</a></td>
                <td class="col-4">Gong / Zhong</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-3m-springboard"> Women&#39;s 3m Springboard</a></td>
                <td class="col-4">SHI Tingmao</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-75kg"> Women&#39;s +75kg</a></td>
                <td class="col-4">MENG Suping</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-3m-springboard"> Men&#39;s 3m Springboard</a></td>
                <td class="col-4">CAO Yuan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">China</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">China</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-58kg"> Men -58kg</a></td>
                <td class="col-4">ZHAO Shuai</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-10m-platform"> Women&#39;s 10m Platform</a></td>
                <td class="col-4">REN Qian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mens-doubles"> Men&#39;s Doubles</a></td>
                <td class="col-4">Zhang / Fu</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-20km-walk"> Women&#39;s 20km Walk</a></td>
                <td class="col-4">LIU Hong</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-69kg"> Men&#39;s 69kg</a></td>
                <td class="col-4">SHI Zhiyong</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">CHEN Long</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-10m-platform"> Men&#39;s 10m Platform</a></td>
                <td class="col-4">CHEN Aisen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg-2"> Women +67kg</a></td>
                <td class="col-4">ZHENG Shuyin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Volleyball</strong></td>
                <td class="col-3"><a href="/en/volleyball-standings-vo-women"> Women</a></td>
                <td class="col-4">China</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-rifle-women"> 10m Air Rifle Women</a></td>
                <td class="col-4">DU Li</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-400m-freestyle"> Men&#39;s 400m Freestyle</a></td>
                <td class="col-4">SUN Yang</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-backstroke"> Men&#39;s 100m Backstroke</a></td>
                <td class="col-4">XU Jiayu</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-77kg"> Men&#39;s 77kg</a></td>
                <td class="col-4">LYU Xiaojun</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">LI Xiaoxia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-3-positions-women"> 50m Rifle 3 Positions Women</a></td>
                <td class="col-4">ZHANG Binbin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-epee-team"> Women&#39;s &#201;p&#233;e Team</a></td>
                <td class="col-4">China</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">ZHANG Jike</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-20km-walk"> Men&#39;s 20km Walk</a></td>
                <td class="col-4">CAI Zelin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-85kg"> Men&#39;s 85kg</a></td>
                <td class="col-4">TIAN Tao</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Trampoline Gymnastics</strong></td>
                <td class="col-3"><a href="/en/trampoline-gymnastics-standings-gt-men"> Men</a></td>
                <td class="col-4">DONG Dong</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-rs-x-women"> RS:X Women</a></td>
                <td class="col-4">CHEN Peina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-3m-springboard"> Women&#39;s 3m Springboard</a></td>
                <td class="col-4">HE Zi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-hammer-throw"> Women&#39;s Hammer Throw</a></td>
                <td class="col-4">ZHANG Wenxiu</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Synchronised Swimming</strong></td>
                <td class="col-3"><a href="/en/synchronised-swimming-standings-sy-duets"> Duets</a></td>
                <td class="col-4">Huang / Sun</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-10m-platform"> Women&#39;s 10m Platform</a></td>
                <td class="col-4">SI Yajie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Synchronised Swimming</strong></td>
                <td class="col-3"><a href="/en/synchronised-swimming-standings-sy-teams"> Teams</a></td>
                <td class="col-4">China</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-light-57-60kg"> Women&#39;s Light (57-60kg)</a></td>
                <td class="col-4">YIN Junhua</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-rifle-women"> 10m Air Rifle Women</a></td>
                <td class="col-4">YI Siling</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-pistol-men"> 10m Air Pistol Men</a></td>
                <td class="col-4">PANG Wei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-epee-individual"> Women&#39;s &#201;p&#233;e Individual</a></td>
                <td class="col-4">SUN Yiwen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">China</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-backstroke"> Women&#39;s 100m Backstroke</a></td>
                <td class="col-4">FU Yuanhui</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">China</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-synchronised-3m-springboard"> Men&#39;s Synchronised 3m Springboard</a></td>
                <td class="col-4">Cao / Qin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-90-kg"> Men -90 kg</a></td>
                <td class="col-4">CHENG Xunzhao</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-3-positions-women"> 50m Rifle 3 Positions Women</a></td>
                <td class="col-4">DU Li</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-breaststroke"> Women&#39;s 200m Breaststroke</a></td>
                <td class="col-4">SHI Jinglin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-individual-medley"> Men&#39;s 200m Individual Medley</a></td>
                <td class="col-4">WANG Shun</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-womens-double-sculls"> Lightweight Women&#39;s Double Sculls</a></td>
                <td class="col-4">Huang / Pan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Trampoline Gymnastics</strong></td>
                <td class="col-3"><a href="/en/trampoline-gymnastics-standings-gt-women"> Women</a></td>
                <td class="col-4">LI Dan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg-2"> Women +78 kg</a></td>
                <td class="col-4">YU Song</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-single-sculls"> Women&#39;s Single Sculls</a></td>
                <td class="col-4">DUAN Jingli</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-25m-rapid-fire-pistol-men"> 25m Rapid Fire Pistol Men</a></td>
                <td class="col-4">LI Yuehong</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Trampoline Gymnastics</strong></td>
                <td class="col-3"><a href="/en/trampoline-gymnastics-standings-gt-men"> Men</a></td>
                <td class="col-4">GAO Lei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-triple-jump"> Men&#39;s Triple Jump</a></td>
                <td class="col-4">DONG Bin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mixed-doubles"> Mixed Doubles</a></td>
                <td class="col-4">Zhang / Zhao</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-48-kg"> Women&#39;s Freestyle 48 kg</a></td>
                <td class="col-4">SUN Yanan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-75-kg"> Women&#39;s Freestyle 75 kg</a></td>
                <td class="col-4">ZHANG Fengliu</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-20km-walk"> Women&#39;s 20km Walk</a></td>
                <td class="col-4">LU Xiuzhi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Golf</strong></td>
                <td class="col-3"><a href="/en/golf-standings-go-womens-individual-stroke-play"> Women&#39;s Individual Stroke Play</a></td>
                <td class="col-4">FENG Shanshan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-fly-48-51kg"> Women&#39;s Fly (48-51kg)</a></td>
                <td class="col-4">REN Cancan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-middle-69-75kg"> Women&#39;s Middle (69-75kg)</a></td>
                <td class="col-4">LI Qian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-fly-52kg"> Men&#39;s Fly (52kg)</a></td>
                <td class="col-4">HU Jianguan</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-60-kg"> Men -60 kg</a></td>
                <td class="col-4">MUDRANOV Beslan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-sabre-individual"> Women&#39;s Sabre Individual</a></td>
                <td class="col-4">EGORIAN Yana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-81-kg"> Men -81 kg</a></td>
                <td class="col-4">KHALMURZAEV Khasan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-foil-individual"> Women&#39;s Foil Individual</a></td>
                <td class="col-4">DERIGLAZOVA Inna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-foil-team"> Men&#39;s Foil Team</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-sabre-team"> Women&#39;s Sabre Team</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-womens-doubles"> Women&#39;s Doubles</a></td>
                <td class="col-4">Makarova / Vesnina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-uneven-bars"> Women&#39;s Uneven Bars</a></td>
                <td class="col-4">MUSTAFINA Aliya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-75-kg"> Men&#39;s Greco-Roman 75 kg</a></td>
                <td class="col-4">VLASOV Roman</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-85-kg"> Men&#39;s Greco-Roman 85 kg</a></td>
                <td class="col-4">CHAKVETADZE Davit</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-heavy-91kg"> Men&#39;s Heavy (91kg)</a></td>
                <td class="col-4">TISHCHENKO Evgeny</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Synchronised Swimming</strong></td>
                <td class="col-3"><a href="/en/synchronised-swimming-standings-sy-duets"> Duets</a></td>
                <td class="col-4">Ishchenko / Romashina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Synchronised Swimming</strong></td>
                <td class="col-3"><a href="/en/synchronised-swimming-standings-sy-teams"> Teams</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Handball</strong></td>
                <td class="col-3"><a href="/en/handball-standings-hb-women"> Women</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-86-kg"> Men&#39;s Freestyle 86 kg</a></td>
                <td class="col-4">SADULAEV Abdulrashid</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rhythmic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/rhythmic-gymnastics-standings-gr-individual-all-around"> Individual All-Around</a></td>
                <td class="col-4">MAMUN Margarita</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Modern Pentathlon</strong></td>
                <td class="col-3"><a href="/en/modern-pentathlon-standings-mp-mens-individual"> Men&#39;s Individual</a></td>
                <td class="col-4">LESUN Alexander</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rhythmic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/rhythmic-gymnastics-standings-gr-group-all-around"> Group All-Around</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-65-kg"> Men&#39;s Freestyle 65 kg</a></td>
                <td class="col-4">RAMONOV Soslan</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-pistol-women"> 10m Air Pistol Women</a></td>
                <td class="col-4">BATSARASHKINA Vitalina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-sabre-individual"> Women&#39;s Sabre Individual</a></td>
                <td class="col-4">VELIKAYA Sofya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-breaststroke"> Women&#39;s 100m Breaststroke</a></td>
                <td class="col-4">EFIMOVA Yulia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-womens-individual-time-trial"> Women&#39;s Individual Time Trial</a></td>
                <td class="col-4">ZABELINSKAYA Olga</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-breaststroke"> Women&#39;s 200m Breaststroke</a></td>
                <td class="col-4">EFIMOVA Yulia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-team-sprint"> Women&#39;s Team Sprint</a></td>
                <td class="col-4">Voinova / Shmeleva</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-3-positions-men"> 50m Rifle 3 Positions Men</a></td>
                <td class="col-4">KAMENSKIY Sergey</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-vault"> Women&#39;s Vault</a></td>
                <td class="col-4">PASEKA Maria</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-vault"> Men&#39;s Vault</a></td>
                <td class="col-4">ABLIAZIN Denis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-58-kg"> Women&#39;s Freestyle 58 kg</a></td>
                <td class="col-4">KOBLOVA ZHOLOBOVA Valeriia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-69-kg"> Women&#39;s Freestyle 69 kg</a></td>
                <td class="col-4">VOROBEVA Natalia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-68kg"> Men -68kg</a></td>
                <td class="col-4">DENISENKO Alexey</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-74-kg"> Men&#39;s Freestyle 74 kg</a></td>
                <td class="col-4">GEDUEV Aniuar</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rhythmic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/rhythmic-gymnastics-standings-gr-individual-all-around"> Individual All-Around</a></td>
                <td class="col-4">KUDRYAVTSEVA Yana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-fly-52kg"> Men&#39;s Fly (52kg)</a></td>
                <td class="col-4">ALOIAN Misha</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-52-kg"> Women -52 kg</a></td>
                <td class="col-4">KUZIUTINA Natalia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-foil-individual"> Men&#39;s Foil Individual</a></td>
                <td class="col-4">SAFIN Timur</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-rifle-men"> 10m Air Rifle Men</a></td>
                <td class="col-4">MASLENNIKOV Vladimir</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-breaststroke"> Men&#39;s 200m Breaststroke</a></td>
                <td class="col-4">CHUPKOV Anton</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-individual-all-around"> Women&#39;s Individual All-Around</a></td>
                <td class="col-4">MUSTAFINA Aliya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-epee-team"> Women&#39;s &#201;p&#233;e Team</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-backstroke"> Men&#39;s 200m Backstroke</a></td>
                <td class="col-4">RYLOV Evgeny</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-prone-men"> 50m Rifle Prone Men</a></td>
                <td class="col-4">GRIGORYAN Kirill</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-rs-x-women"> RS:X Women</a></td>
                <td class="col-4">ELFUTINA Stefaniya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-sprint"> Men&#39;s Sprint</a></td>
                <td class="col-4">DMITRIEV Denis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-rings"> Men&#39;s Rings</a></td>
                <td class="col-4">ABLIAZIN Denis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-130-kg"> Men&#39;s Greco-Roman 130 kg</a></td>
                <td class="col-4">SEMENOV Sergey</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-1000m"> Men&#39;s Kayak Single 1000m</a></td>
                <td class="col-4">ANOSHKIN Roman</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-parallel-bars"> Men&#39;s Parallel Bars</a></td>
                <td class="col-4">BELYAVSKIY David</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-75-kg"> Women&#39;s Freestyle 75 kg</a></td>
                <td class="col-4">BUKINA Ekaterina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-light-57-60kg"> Women&#39;s Light (57-60kg)</a></td>
                <td class="col-4">BELIAKOVA Anastasiia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Water Polo</strong></td>
                <td class="col-3"><a href="/en/water-polo-standings-wp-women"> Women</a></td>
                <td class="col-4">Russian Federation</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-bantam-56kg"> Men&#39;s Bantam (56kg)</a></td>
                <td class="col-4">NIKITIN Vladimir</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-welter-64kg"> Men&#39;s Light Welter (64kg)</a></td>
                <td class="col-4">DUNAYTSEV Vitaly</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-eventing-individual"> Eventing Individual</a></td>
                <td class="col-4">JUNG Michael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-quadruple-sculls"> Men&#39;s Quadruple Sculls</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-quadruple-sculls"> Women&#39;s Quadruple Sculls</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-3-positions-women"> 50m Rifle 3 Positions Women</a></td>
                <td class="col-4">ENGLEDER Barbara</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-prone-men"> 50m Rifle Prone Men</a></td>
                <td class="col-4">JUNGHAENEL Henri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-dressage-team"> Dressage Team</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-discus-throw"> Men&#39;s Discus Throw</a></td>
                <td class="col-4">HARTING Christoph</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-25m-rapid-fire-pistol-men"> 25m Rapid Fire Pistol Men</a></td>
                <td class="col-4">REITZ Christian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-horizontal-bar"> Men&#39;s Horizontal Bar</a></td>
                <td class="col-4">HAMBUECHEN Fabian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-sprint"> Women&#39;s Sprint</a></td>
                <td class="col-4">VOGEL Kristina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Beach Volleyball</strong></td>
                <td class="col-3"><a href="/en/beach-volleyball-standings-bv-women"> Women</a></td>
                <td class="col-4">Ludwig / Walkenhorst</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-double-1000m"> Men&#39;s Kayak Double 1000m</a></td>
                <td class="col-4">Gross / Rendschmidt</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-single-1000m"> Men&#39;s Canoe Single 1000m</a></td>
                <td class="col-4">BRENDEL Sebastian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Football</strong></td>
                <td class="col-3"><a href="/en/football-standings-fb-women"> Women</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-double-1000m"> Men&#39;s Canoe Double 1000m</a></td>
                <td class="col-4">Brendel / Vandrey</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-four-1000m"> Men&#39;s Kayak Four 1000m</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-javelin-throw"> Men&#39;s Javelin Throw</a></td>
                <td class="col-4">ROHLER Thomas</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-eventing-team"> Eventing Team</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-25m-pistol-women"> 25m Pistol Women</a></td>
                <td class="col-4">KARSCH Monika</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-womens-individual"> Women&#39;s Individual</a></td>
                <td class="col-4">UNRUH Lisa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-eight"> Men&#39;s Eight</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">KERBER Angelique</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-dressage-individual"> Dressage Individual</a></td>
                <td class="col-4">WERTH Isabell</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-double-500m"> Women&#39;s Kayak Double 500m</a></td>
                <td class="col-4">Dietze / Weber</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-four-500m"> Women&#39;s Kayak Four 500m</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Football</strong></td>
                <td class="col-3"><a href="/en/football-standings-fb-men"> Men</a></td>
                <td class="col-4">Germany</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-70-kg"> Women -70 kg</a></td>
                <td class="col-4">VARGAS KOCH Laura</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-team-sprint"> Women&#39;s Team Sprint</a></td>
                <td class="col-4">Vogel / Welte</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-discus-throw"> Men&#39;s Discus Throw</a></td>
                <td class="col-4">JASINSKI Daniel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-uneven-bars"> Women&#39;s Uneven Bars</a></td>
                <td class="col-4">SCHEDER Sophie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-dressage-individual"> Dressage Individual</a></td>
                <td class="col-4">BRORING-SPREHE Kristina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-85-kg"> Men&#39;s Greco-Roman 85 kg</a></td>
                <td class="col-4">KUDLA Denis Maksymilian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-3m-springboard"> Men&#39;s 3m Springboard</a></td>
                <td class="col-4">HAUSDING Patrick</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-jumping-team"> Jumping Team</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-49er-men"> 49er Men</a></td>
                <td class="col-4">Heil / Ploessel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Hockey</strong></td>
                <td class="col-3"><a href="/en/hockey-standings-ho-men"> Men</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Hockey</strong></td>
                <td class="col-3"><a href="/en/hockey-standings-ho-women"> Women</a></td>
                <td class="col-4">Germany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-200m"> Men&#39;s Kayak Single 200m</a></td>
                <td class="col-4">RAUHE Ronald</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-welter-64kg"> Men&#39;s Light Welter (64kg)</a></td>
                <td class="col-4">HARUTYUNYAN Artem</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Handball</strong></td>
                <td class="col-3"><a href="/en/handball-standings-hb-men"> Men</a></td>
                <td class="col-4">Germany</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-400m-individual-medley"> Men&#39;s 400m Individual Medley</a></td>
                <td class="col-4">HAGINO Kosuke</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-73-kg"> Men -73 kg</a></td>
                <td class="col-4">ONO Shohei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">Japan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-70-kg"> Women -70 kg</a></td>
                <td class="col-4">TACHIMOTO Haruka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-90-kg"> Men -90 kg</a></td>
                <td class="col-4">BAKER Mashu</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-individual-all-around"> Men&#39;s Individual All-Around</a></td>
                <td class="col-4">UCHIMURA Kohei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-breaststroke"> Women&#39;s 200m Breaststroke</a></td>
                <td class="col-4">KANETO Rie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-48-kg"> Women&#39;s Freestyle 48 kg</a></td>
                <td class="col-4">TOSAKA Eri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-58-kg"> Women&#39;s Freestyle 58 kg</a></td>
                <td class="col-4">ICHO Kaori</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-69-kg"> Women&#39;s Freestyle 69 kg</a></td>
                <td class="col-4">DOSHO Sara</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-womens-doubles"> Women&#39;s Doubles</a></td>
                <td class="col-4">Takahashi / Matsutomo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-63-kg"> Women&#39;s Freestyle 63 kg</a></td>
                <td class="col-4">KAWAI Risako</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-butterfly"> Men&#39;s 200m Butterfly</a></td>
                <td class="col-4">SAKAI Masato</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-individual-medley"> Men&#39;s 200m Individual Medley</a></td>
                <td class="col-4">HAGINO Kosuke</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg-2"> Men +100 kg</a></td>
                <td class="col-4">HARASAWA Hisayoshi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-59-kg"> Men&#39;s Greco-Roman 59 kg</a></td>
                <td class="col-4">OTA Shinobu</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">Japan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-53-kg"> Women&#39;s Freestyle 53 kg</a></td>
                <td class="col-4">YOSHIDA Saori</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-57-kg"> Men&#39;s Freestyle 57 kg</a></td>
                <td class="col-4">HIGUCHI Rei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-4-x-100m-relay"> Men&#39;s 4 x 100m Relay</a></td>
                <td class="col-4">Japan</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-48-kg"> Women -48 kg</a></td>
                <td class="col-4">KONDO Ami</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-60-kg"> Men -60 kg</a></td>
                <td class="col-4">TAKATO Naohisa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-48kg"> Women&#39;s 48kg</a></td>
                <td class="col-4">MIYAKE Hiromi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-400m-individual-medley"> Men&#39;s 400m Individual Medley</a></td>
                <td class="col-4">SETO Daiya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-52-kg"> Women -52 kg</a></td>
                <td class="col-4">NAKAMURA Misato</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-66-kg"> Men -66 kg</a></td>
                <td class="col-4">EBINUMA Masashi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-57-kg"> Women -57 kg</a></td>
                <td class="col-4">MATSUMOTO Kaori</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-canoe-single-c1-men"> Canoe Single (C1) Men</a></td>
                <td class="col-4">HANEDA Takuya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-81-kg"> Men -81 kg</a></td>
                <td class="col-4">NAGASE Takanori</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-200m-freestyle-relay"> Men&#39;s 4 x 200m Freestyle Relay</a></td>
                <td class="col-4">Japan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-butterfly"> Women&#39;s 200m Butterfly</a></td>
                <td class="col-4">HOSHI Natsumi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg"> Men -100 kg</a></td>
                <td class="col-4">HAGA Ryunosuke</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">MIZUTANI Jun</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg-2"> Women +78 kg</a></td>
                <td class="col-4">YAMABE Kanae</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">NISHIKORI Kei</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-vault"> Men&#39;s Vault</a></td>
                <td class="col-4">SHIRAI Kenzo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Synchronised Swimming</strong></td>
                <td class="col-3"><a href="/en/synchronised-swimming-standings-sy-duets"> Duets</a></td>
                <td class="col-4">Inui / Mitsui</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">Japan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">OKUHARA Nozomi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Synchronised Swimming</strong></td>
                <td class="col-3"><a href="/en/synchronised-swimming-standings-sy-teams"> Teams</a></td>
                <td class="col-4">Japan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-50km-walk"> Men&#39;s 50km Walk</a></td>
                <td class="col-4">ARAI Hirooki</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-eventing-team"> Eventing Team</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-canoe-single-c1-men"> Canoe Single (C1) Men</a></td>
                <td class="col-4">GARGAUD CHANUT Denis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-mens-double-sculls"> Lightweight Men&#39;s Double Sculls</a></td>
                <td class="col-4">Azou / Houin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg-2"> Women +78 kg</a></td>
                <td class="col-4">ANDEOL Emilie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg-2"> Men +100 kg</a></td>
                <td class="col-4">RINER Teddy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-rs-x-women"> RS:X Women</a></td>
                <td class="col-4">PICON Charline</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-epee-team"> Men&#39;s &#201;p&#233;e Team</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-jumping-team"> Jumping Team</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-light-57-60kg"> Women&#39;s Light (57-60kg)</a></td>
                <td class="col-4">MOSSELY Estelle</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-super-heavy-91kg"> Men&#39;s Super Heavy (+91kg)</a></td>
                <td class="col-4">YOKA Tony Victor James</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-100m-freestyle-relay"> Men&#39;s 4 x 100m Freestyle Relay</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-eventing-individual"> Eventing Individual</a></td>
                <td class="col-4">NICOLAS Astier</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-63-kg"> Women -63 kg</a></td>
                <td class="col-4">AGBEGNENOU Clarisse</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg"> Women -78 kg</a></td>
                <td class="col-4">TCHEUMEO Audrey</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-mens-individual"> Men&#39;s Individual</a></td>
                <td class="col-4">VALLADONT Jean-Charles</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-foil-team"> Men&#39;s Foil Team</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-50m-freestyle"> Men&#39;s 50m Freestyle</a></td>
                <td class="col-4">MANAUDOU Florent</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-25m-rapid-fire-pistol-men"> 25m Rapid Fire Pistol Men</a></td>
                <td class="col-4">QUIQUAMPOIX Jean</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-pole-vault"> Men&#39;s Pole Vault</a></td>
                <td class="col-4">LAVILLENIE Renaud</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-discus-throw"> Women&#39;s Discus Throw</a></td>
                <td class="col-4">ROBERT-MICHON Melina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-60kg"> Men&#39;s Light (60kg)</a></td>
                <td class="col-4">OUMIHA Sofiane</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-decathlon"> Men&#39;s Decathlon</a></td>
                <td class="col-4">MAYER Kevin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Modern Pentathlon</strong></td>
                <td class="col-3"><a href="/en/modern-pentathlon-standings-mp-womens-individual"> Women&#39;s Individual</a></td>
                <td class="col-4">CLOUVEL Elodie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg"> Women -67kg</a></td>
                <td class="col-4">NIARE Haby</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-200m"> Men&#39;s Kayak Single 200m</a></td>
                <td class="col-4">BEAUMONT Maxime</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-fly-48-51kg"> Women&#39;s Fly (48-51kg)</a></td>
                <td class="col-4">OURAHMOUNE Sarah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Handball</strong></td>
                <td class="col-3"><a href="/en/handball-standings-hb-women"> Women</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Handball</strong></td>
                <td class="col-3"><a href="/en/handball-standings-hb-men"> Men</a></td>
                <td class="col-4">France</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-epee-individual"> Men&#39;s &#201;p&#233;e Individual</a></td>
                <td class="col-4">GRUMIER Gauthier</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-mens-four"> Lightweight Men&#39;s Four</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-canoe-double-c2-men"> Canoe Double (C2) Men</a></td>
                <td class="col-4">Peche / Klauss</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg"> Men -100 kg</a></td>
                <td class="col-4">MARET Cyrille</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-team-sprint"> Men&#39;s Team Sprint</a></td>
                <td class="col-4">France</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-3-positions-men"> 50m Rifle 3 Positions Men</a></td>
                <td class="col-4">RAYNAUD Alexis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-rs-x-men"> RS:X Men</a></td>
                <td class="col-4">LE COQ Pierre</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Marathon Swimming</strong></td>
                <td class="col-3"><a href="/en/marathon-swimming-standings-ow-mens-10km"> Men&#39;s 10km</a></td>
                <td class="col-4">OLIVIER Marc-Antoine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-110m-hurdles"> Men&#39;s 110m Hurdles</a></td>
                <td class="col-4">BASCOU Dimitri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-welter-69kg"> Men&#39;s Welter (69kg)</a></td>
                <td class="col-4">CISSOKHO Souleymane Diop</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-3000m-steeplechase"> Men&#39;s 3000m Steeplechase</a></td>
                <td class="col-4">MEKHISSI Mahiedine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-heavy-81kg"> Men&#39;s Light Heavy (81kg)</a></td>
                <td class="col-4">BAUDERLIQUE Mathieu Albert Daniel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-470-women"> 470 Women</a></td>
                <td class="col-4">Defrance / Lecointre</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-200m"> Men&#39;s 200m</a></td>
                <td class="col-4">LEMAITRE Christophe</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">Republic of Korea</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">Republic of Korea</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-epee-individual"> Men&#39;s &#201;p&#233;e Individual</a></td>
                <td class="col-4">PARK Sangyoung</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-pistol-men"> 50m Pistol Men</a></td>
                <td class="col-4">JIN Jongoh</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-womens-individual"> Women&#39;s Individual</a></td>
                <td class="col-4">CHANG Hyejin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-mens-individual"> Men&#39;s Individual</a></td>
                <td class="col-4">KU Bonchan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-49kg"> Women -49kg</a></td>
                <td class="col-4">KIM Sohui</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg"> Women -67kg</a></td>
                <td class="col-4">OH Hyeri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Golf</strong></td>
                <td class="col-3"><a href="/en/golf-standings-go-womens-individual-stroke-play"> Women&#39;s Individual Stroke Play</a></td>
                <td class="col-4">PARK Inbee</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-48-kg"> Women -48 kg</a></td>
                <td class="col-4">JEONG Bokyeong</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-66-kg"> Men -66 kg</a></td>
                <td class="col-4">AN Baul</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-prone-men"> 50m Rifle Prone Men</a></td>
                <td class="col-4">KIM Jonghyun</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-53kg"> Women&#39;s 53kg</a></td>
                <td class="col-4">YOON Jin Hee</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-90-kg"> Men -90 kg</a></td>
                <td class="col-4">GWAK Donghan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-sabre-individual"> Men&#39;s Sabre Individual</a></td>
                <td class="col-4">KIM Junghwan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-womens-individual"> Women&#39;s Individual</a></td>
                <td class="col-4">KI Bobae</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-75-kg"> Men&#39;s Greco-Roman 75 kg</a></td>
                <td class="col-4">KIM Hyeonwoo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-58kg"> Men -58kg</a></td>
                <td class="col-4">KIM Taehun</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-womens-doubles"> Women&#39;s Doubles</a></td>
                <td class="col-4">Jung / Shin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-68kg"> Men -68kg</a></td>
                <td class="col-4">LEE Daehoon</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg-2"> Men +80kg</a></td>
                <td class="col-4">CHA Dongmin</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-66-kg"> Men -66 kg</a></td>
                <td class="col-4">BASILE Fabio</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-foil-individual"> Men&#39;s Foil Individual</a></td>
                <td class="col-4">GAROZZO Daniele</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-rifle-men"> 10m Air Rifle Men</a></td>
                <td class="col-4">CAMPRIANI Niccolo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-skeet-women"> Skeet Women</a></td>
                <td class="col-4">BACOSI Diana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-skeet-men"> Skeet Men</a></td>
                <td class="col-4">ROSSETTI Gabriele</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-1500m-freestyle"> Men&#39;s 1500m Freestyle</a></td>
                <td class="col-4">PALTRINIERI Gregorio</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-rifle-3-positions-men"> 50m Rifle 3 Positions Men</a></td>
                <td class="col-4">CAMPRIANI Niccolo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-omnium"> Men&#39;s Omnium</a></td>
                <td class="col-4">VIVIANI Elia</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-epee-individual"> Women&#39;s &#201;p&#233;e Individual</a></td>
                <td class="col-4">FIAMINGO Rossella</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-synchronised-3m-springboard"> Women&#39;s Synchronised 3m Springboard</a></td>
                <td class="col-4">Cagnotto / Dallape&#39;</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-52-kg"> Women -52 kg</a></td>
                <td class="col-4">GIUFFRIDA Odette</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-trap-men"> Trap Men</a></td>
                <td class="col-4">PELLIELO Giovanni</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-double-trap-men"> Double Trap Men</a></td>
                <td class="col-4">INNOCENTI Marco</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-foil-individual"> Women&#39;s Foil Individual</a></td>
                <td class="col-4">DI FRANCISCA Elisa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-skeet-women"> Skeet Women</a></td>
                <td class="col-4">CAINERO Chiara</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-epee-team"> Men&#39;s &#201;p&#233;e Team</a></td>
                <td class="col-4">Italy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Marathon Swimming</strong></td>
                <td class="col-3"><a href="/en/marathon-swimming-standings-ow-womens-10km"> Women&#39;s 10km</a></td>
                <td class="col-4">BRUNI Rachele</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Beach Volleyball</strong></td>
                <td class="col-3"><a href="/en/beach-volleyball-standings-bv-men"> Men</a></td>
                <td class="col-4">Nicolai / Lupo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Water Polo</strong></td>
                <td class="col-3"><a href="/en/water-polo-standings-wp-women"> Women</a></td>
                <td class="col-4">Italy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Volleyball</strong></td>
                <td class="col-3"><a href="/en/volleyball-standings-vo-men"> Men</a></td>
                <td class="col-4">Italy</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-400m-freestyle"> Men&#39;s 400m Freestyle</a></td>
                <td class="col-4">DETTI Gabriele</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-womens-road-race"> Women&#39;s Road Race</a></td>
                <td class="col-4">LONGO BORGHINI Elisa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-pair"> Men&#39;s Pair</a></td>
                <td class="col-4">DI COSTANZO / Abagnale</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-four"> Men&#39;s Four</a></td>
                <td class="col-4">Italy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-1500m-freestyle"> Men&#39;s 1500m Freestyle</a></td>
                <td class="col-4">DETTI Gabriele</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-3m-springboard"> Women&#39;s 3m Springboard</a></td>
                <td class="col-4">CAGNOTTO Tania</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Water Polo</strong></td>
                <td class="col-3"><a href="/en/water-polo-standings-wp-men"> Men</a></td>
                <td class="col-4">Italy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-65-kg"> Men&#39;s Freestyle 65 kg</a></td>
                <td class="col-4">CHAMIZO MARQUEZ Frank</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-400m-freestyle"> Men&#39;s 400m Freestyle</a></td>
                <td class="col-4">HORTON Mack</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-100m-freestyle-relay"> Women&#39;s 4 x 100m Freestyle Relay</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-trap-women"> Trap Women</a></td>
                <td class="col-4">SKINNER Catherine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rugby Sevens</strong></td>
                <td class="col-3"><a href="/en/rugby-sevens-standings-ru-women"> Women</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-freestyle"> Men&#39;s 100m Freestyle</a></td>
                <td class="col-4">CHALMERS Kyle</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-single-sculls"> Women&#39;s Single Sculls</a></td>
                <td class="col-4">BRENNAN Kimberley</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-laser-men"> Laser Men</a></td>
                <td class="col-4">BURTON Tom</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Modern Pentathlon</strong></td>
                <td class="col-3"><a href="/en/modern-pentathlon-standings-mp-womens-individual"> Women&#39;s Individual</a></td>
                <td class="col-4">ESPOSITO Chloe</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-butterfly"> Women&#39;s 200m Butterfly</a></td>
                <td class="col-4">GROVES Madeline</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-200m-freestyle-relay"> Women&#39;s 4 x 200m Freestyle Relay</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-quadruple-sculls"> Men&#39;s Quadruple Sculls</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-backstroke"> Men&#39;s 200m Backstroke</a></td>
                <td class="col-4">LARKIN Mitchell</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-four"> Men&#39;s Four</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-team-pursuit"> Men&#39;s Team Pursuit</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-100m-medley-relay"> Women&#39;s 4 x 100m Medley Relay</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-470-men"> 470 Men</a></td>
                <td class="col-4">Belcher / Ryan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-49er-men"> 49er Men</a></td>
                <td class="col-4">Outteridge / Jensen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-nacra-17-mixed"> Nacra 17 Mixed</a></td>
                <td class="col-4">Darmanin / Waterhouse</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-50km-walk"> Men&#39;s 50km Walk</a></td>
                <td class="col-4">TALLENT Jared</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-mens-team"> Men&#39;s Team</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-synchronised-3m-springboard"> Women&#39;s Synchronised 3m Springboard</a></td>
                <td class="col-4">Keeney / Smith</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-100m-freestyle-relay"> Men&#39;s 4 x 100m Freestyle Relay</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-eventing-team"> Eventing Team</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-freestyle"> Women&#39;s 200m Freestyle</a></td>
                <td class="col-4">MCKEON Emma</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-kayak-k1-women"> Kayak (K1) Women</a></td>
                <td class="col-4">FOX Jessica</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-20km-walk"> Men&#39;s 20km Walk</a></td>
                <td class="col-4">BIRD-SMITH Dane</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-keirin"> Women&#39;s Keirin</a></td>
                <td class="col-4">MEARES Anna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-4-x-100m-medley-relay"> Men&#39;s 4 x 100m Medley Relay</a></td>
                <td class="col-4">Australia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-double-1000m"> Men&#39;s Kayak Double 1000m</a></td>
                <td class="col-4">Tame / Wallace</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-womens-road-race"> Women&#39;s Road Race</a></td>
                <td class="col-4">VAN DER BREGGEN Anna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-womens-double-sculls"> Lightweight Women&#39;s Double Sculls</a></td>
                <td class="col-4">Head / Paulis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-keirin"> Women&#39;s Keirin</a></td>
                <td class="col-4">LIGTLEE Elis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-rs-x-men"> RS:X Men</a></td>
                <td class="col-4">VAN RIJSSELBERGHE Dorian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Marathon Swimming</strong></td>
                <td class="col-3"><a href="/en/marathon-swimming-standings-ow-womens-10km"> Women&#39;s 10km</a></td>
                <td class="col-4">VAN ROUWENDAAL Sharon</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-balance-beam"> Women&#39;s Balance Beam</a></td>
                <td class="col-4">WEVERS Sanne</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Marathon Swimming</strong></td>
                <td class="col-3"><a href="/en/marathon-swimming-standings-ow-mens-10km"> Men&#39;s 10km</a></td>
                <td class="col-4">WEERTMAN Ferry</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-laser-radial-women"> Laser Radial Women</a></td>
                <td class="col-4">BOUWMEESTER Marit</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-mens-individual-time-trial"> Men&#39;s Individual Time Trial</a></td>
                <td class="col-4">DUMOULIN Tom</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-quadruple-sculls"> Women&#39;s Quadruple Sculls</a></td>
                <td class="col-4">Netherlands</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-keirin"> Men&#39;s Keirin</a></td>
                <td class="col-4">BUCHLI Matthijs</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-200m"> Women&#39;s 200m</a></td>
                <td class="col-4">SCHIPPERS Dafne</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling BMX</strong></td>
                <td class="col-3"><a href="/en/cycling-bmx-standings-cb-men"> Men</a></td>
                <td class="col-4">VAN GORKOM Jelle</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Hockey</strong></td>
                <td class="col-3"><a href="/en/hockey-standings-ho-women"> Women</a></td>
                <td class="col-4">Netherlands</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-middle-69-75kg"> Women&#39;s Middle (69-75kg)</a></td>
                <td class="col-4">FONTIJN Nouchka</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-63-kg"> Women -63 kg</a></td>
                <td class="col-4">VAN EMDEN Anicka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-womens-individual-time-trial"> Women&#39;s Individual Time Trial</a></td>
                <td class="col-4">VAN DER BREGGEN Anna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-eight"> Men&#39;s Eight</a></td>
                <td class="col-4">Netherlands</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Beach Volleyball</strong></td>
                <td class="col-3"><a href="/en/beach-volleyball-standings-bv-men"> Men</a></td>
                <td class="col-4">Meeuwsen / Brouwer</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-epee-individual"> Women&#39;s &#201;p&#233;e Individual</a></td>
                <td class="col-4">SZASZ Emese</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-400m-individual-medley"> Women&#39;s 400m Individual Medley</a></td>
                <td class="col-4">HOSSZU Katinka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-backstroke"> Women&#39;s 100m Backstroke</a></td>
                <td class="col-4">HOSSZU Katinka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-individual-medley"> Women&#39;s 200m Individual Medley</a></td>
                <td class="col-4">HOSSZU Katinka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-sabre-individual"> Men&#39;s Sabre Individual</a></td>
                <td class="col-4">SZILAGYI Aron</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-double-500m"> Women&#39;s Kayak Double 500m</a></td>
                <td class="col-4">Kozak / Szabo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-single-500m"> Women&#39;s Kayak Single 500m</a></td>
                <td class="col-4">KOZAK Danuta</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-four-500m"> Women&#39;s Kayak Four 500m</a></td>
                <td class="col-4">Hungary</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-epee-individual"> Men&#39;s &#201;p&#233;e Individual</a></td>
                <td class="col-4">IMRE Geza</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-backstroke"> Women&#39;s 200m Backstroke</a></td>
                <td class="col-4">HOSSZU Katinka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-butterfly"> Men&#39;s 100m Butterfly</a></td>
                <td class="col-4">CSEH Laszlo</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-butterfly"> Men&#39;s 200m Butterfly</a></td>
                <td class="col-4">KENDERESI Tamas</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-800m-freestyle"> Women&#39;s 800m Freestyle</a></td>
                <td class="col-4">KAPAS Boglarka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-shot-put"> Women&#39;s Shot Put</a></td>
                <td class="col-4">MARTON Anita</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-mens-epee-team"> Men&#39;s &#201;p&#233;e Team</a></td>
                <td class="col-4">Hungary</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-57-kg"> Women -57 kg</a></td>
                <td class="col-4">SILVA Rafaela</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-pole-vault"> Men&#39;s Pole Vault</a></td>
                <td class="col-4">DA SILVA Thiago Braz</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-60kg"> Men&#39;s Light (60kg)</a></td>
                <td class="col-4">CONCEICAO Robson</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-49er-fx-women"> 49er FX Women</a></td>
                <td class="col-4">Kunze / Grael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Beach Volleyball</strong></td>
                <td class="col-3"><a href="/en/beach-volleyball-standings-bv-men"> Men</a></td>
                <td class="col-4">Cerutti / Oscar Schmidt</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Football</strong></td>
                <td class="col-3"><a href="/en/football-standings-fb-men"> Men</a></td>
                <td class="col-4">Brazil</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Volleyball</strong></td>
                <td class="col-3"><a href="/en/volleyball-standings-vo-men"> Men</a></td>
                <td class="col-4">Brazil</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-pistol-men"> 10m Air Pistol Men</a></td>
                <td class="col-4">WU Felipe Almeida</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-floor-exercise"> Men&#39;s Floor Exercise</a></td>
                <td class="col-4">HYPOLITO Diego</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-rings"> Men&#39;s Rings</a></td>
                <td class="col-4">ZANETTI Arthur</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Beach Volleyball</strong></td>
                <td class="col-3"><a href="/en/beach-volleyball-standings-bv-women"> Women</a></td>
                <td class="col-4">Bednarczuk / Seixas de Freitas</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-single-1000m"> Men&#39;s Canoe Single 1000m</a></td>
                <td class="col-4">QUEIROZ DOS SANTOS Isaquias</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-double-1000m"> Men&#39;s Canoe Double 1000m</a></td>
                <td class="col-4">de Souza Silva / Queiroz dos Santos</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg"> Women -78 kg</a></td>
                <td class="col-4">AGUIAR Mayra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg-2"> Men +100 kg</a></td>
                <td class="col-4">SILVA Rafael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-floor-exercise"> Men&#39;s Floor Exercise</a></td>
                <td class="col-4">MARIANO Arthur</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Marathon Swimming</strong></td>
                <td class="col-3"><a href="/en/marathon-swimming-standings-ow-womens-10km"> Women&#39;s 10km</a></td>
                <td class="col-4">OKIMOTO Poliana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-single-200m"> Men&#39;s Canoe Single 200m</a></td>
                <td class="col-4">QUEIROZ DOS SANTOS Isaquias</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg-2"> Men +80kg</a></td>
                <td class="col-4">SIQUEIRA Maicon</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-butterfly"> Women&#39;s 200m Butterfly</a></td>
                <td class="col-4">BELMONTE GARCIA Mireia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-kayak-k1-women"> Kayak (K1) Women</a></td>
                <td class="col-4">CHOURRAUT Maialen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mens-doubles"> Men&#39;s Doubles</a></td>
                <td class="col-4">Lopez / Nadal</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-1000m"> Men&#39;s Kayak Single 1000m</a></td>
                <td class="col-4">WALZ Marcus</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-double-200m"> Men&#39;s Kayak Double 200m</a></td>
                <td class="col-4">Craviotto / Toro</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">MARIN Carolina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-high-jump"> Women&#39;s High Jump</a></td>
                <td class="col-4">BEITIA Ruth</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-110m-hurdles"> Men&#39;s 110m Hurdles</a></td>
                <td class="col-4">ORTEGA Orlando</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-57kg"> Women -57kg</a></td>
                <td class="col-4">CALVO GOMEZ Eva</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Basketball</strong></td>
                <td class="col-3"><a href="/en/basketball-standings-bk-women"> Women</a></td>
                <td class="col-4">Spain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rhythmic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/rhythmic-gymnastics-standings-gr-group-all-around"> Group All-Around</a></td>
                <td class="col-4">Spain</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-400m-individual-medley"> Women&#39;s 400m Individual Medley</a></td>
                <td class="col-4">BELMONTE GARCIA Mireia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-75kg-2"> Women&#39;s 75kg</a></td>
                <td class="col-4">VALENTIN PEREZ Lidia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-68kg"> Men -68kg</a></td>
                <td class="col-4">GONZALEZ BONILLA Joel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-200m"> Men&#39;s Kayak Single 200m</a></td>
                <td class="col-4">CRAVIOTTO Saul</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Mountain Bike</strong></td>
                <td class="col-3"><a href="/en/cycling-mountain-bike-standings-cm-mens-cross-country"> Men&#39;s Cross-country</a></td>
                <td class="col-4">COLOMA NICOLAS Carlos</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Basketball</strong></td>
                <td class="col-3"><a href="/en/basketball-standings-bk-men"> Men</a></td>
                <td class="col-4">Spain</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-marathon"> Women&#39;s Marathon</a></td>
                <td class="col-4">SUMGONG Jemima Jelagat</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-800m"> Men&#39;s 800m</a></td>
                <td class="col-4">RUDISHA David Lekuta</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-1500m"> Women&#39;s 1500m</a></td>
                <td class="col-4">KIPYEGON Faith Chepngetich</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-3000m-steeplechase"> Men&#39;s 3000m Steeplechase</a></td>
                <td class="col-4">KIPRUTO Conseslus</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-5000m"> Women&#39;s 5000m</a></td>
                <td class="col-4">CHERUIYOT Vivian Jepkemoi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-marathon"> Men&#39;s Marathon</a></td>
                <td class="col-4">KIPCHOGE Eliud</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-10-000m"> Women&#39;s 10,000m</a></td>
                <td class="col-4">CHERUIYOT Vivian Jepkemoi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-10-000m"> Men&#39;s 10,000m</a></td>
                <td class="col-4">TANUI Paul Kipngetich</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-3000m-steeplechase"> Women&#39;s 3000m Steeplechase</a></td>
                <td class="col-4">JEPKEMOI Hyvin Kiyeng</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-400m-hurdles"> Men&#39;s 400m Hurdles</a></td>
                <td class="col-4">TUMUTI Boniface Mucheru</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-5000m"> Women&#39;s 5000m</a></td>
                <td class="col-4">OBIRI Hellen Onsando</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-javelin-throw"> Men&#39;s Javelin Throw</a></td>
                <td class="col-4">YEGO Julius</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-800m"> Women&#39;s 800m</a></td>
                <td class="col-4">WAMBUI Margaret Nyairera</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-100m"> Women&#39;s 100m</a></td>
                <td class="col-4">THOMPSON Elaine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-100m"> Men&#39;s 100m</a></td>
                <td class="col-4">BOLT Usain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-110m-hurdles"> Men&#39;s 110m Hurdles</a></td>
                <td class="col-4">MCLEOD Omar</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-200m"> Women&#39;s 200m</a></td>
                <td class="col-4">THOMPSON Elaine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-200m"> Men&#39;s 200m</a></td>
                <td class="col-4">BOLT Usain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-4-x-100m-relay"> Men&#39;s 4 x 100m Relay</a></td>
                <td class="col-4">Jamaica</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-4-x-100m-relay"> Women&#39;s 4 x 100m Relay</a></td>
                <td class="col-4">Jamaica</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-4-x-400m-relay"> Women&#39;s 4 x 400m Relay</a></td>
                <td class="col-4">Jamaica</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-4-x-400m-relay"> Men&#39;s 4 x 400m Relay</a></td>
                <td class="col-4">Jamaica</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-100m"> Women&#39;s 100m</a></td>
                <td class="col-4">FRASER-PRYCE Shelly-Ann</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-400m"> Women&#39;s 400m</a></td>
                <td class="col-4">JACKSON Shericka</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-trap-men"> Trap Men</a></td>
                <td class="col-4">GLASNOVIC Josip</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-double-sculls"> Men&#39;s Double Sculls</a></td>
                <td class="col-4">Sinkovic / Sinkovic</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-discus-throw"> Women&#39;s Discus Throw</a></td>
                <td class="col-4">PERKOVIC Sandra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-470-men"> 470 Men</a></td>
                <td class="col-4">Fantela / Marenic</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-javelin-throw"> Women&#39;s Javelin Throw</a></td>
                <td class="col-4">KOLAK Sara</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-single-sculls"> Men&#39;s Single Sculls</a></td>
                <td class="col-4">MARTIN Damir</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-laser-men"> Laser Men</a></td>
                <td class="col-4">STIPANOVIC Tonci</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Water Polo</strong></td>
                <td class="col-3"><a href="/en/water-polo-standings-wp-men"> Men</a></td>
                <td class="col-4">Croatia</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-high-jump"> Women&#39;s High Jump</a></td>
                <td class="col-4">VLASIC Blanka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-super-heavy-91kg"> Men&#39;s Super Heavy (+91kg)</a></td>
                <td class="col-4">HRGOVIC Filip</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-59-kg"> Men&#39;s Greco-Roman 59 kg</a></td>
                <td class="col-4">BORRERO MOLINA Ismael</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-130-kg"> Men&#39;s Greco-Roman 130 kg</a></td>
                <td class="col-4">LOPEZ NUNEZ Mijain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-heavy-81kg"> Men&#39;s Light Heavy (81kg)</a></td>
                <td class="col-4">LA CRUZ Julio Cesar</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-bantam-56kg"> Men&#39;s Bantam (56kg)</a></td>
                <td class="col-4">RAMIREZ Robeisy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-middle-75kg"> Men&#39;s Middle (75kg)</a></td>
                <td class="col-4">LOPEZ Arlen</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg-2"> Women +78 kg</a></td>
                <td class="col-4">ORTIZ Idalys</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-98-kg"> Men&#39;s Greco-Roman 98 kg</a></td>
                <td class="col-4">LUGO CABRERA Yasmany Daniel</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-fly-46-49kg"> Men&#39;s Light Fly (46-49kg)</a></td>
                <td class="col-4">ARGILAGOS Joahnys</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-heavy-91kg"> Men&#39;s Heavy (91kg)</a></td>
                <td class="col-4">SAVON Erislandy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-discus-throw"> Women&#39;s Discus Throw</a></td>
                <td class="col-4">CABALLERO Denia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-60kg"> Men&#39;s Light (60kg)</a></td>
                <td class="col-4">ALVAREZ Lazaro Jorge</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-pair"> Men&#39;s Pair</a></td>
                <td class="col-4">Bond / Murray</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-single-sculls"> Men&#39;s Single Sculls</a></td>
                <td class="col-4">DRYSDALE Mahe</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-single-200m"> Women&#39;s Kayak Single 200m</a></td>
                <td class="col-4">CARRINGTON Lisa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-49er-men"> 49er Men</a></td>
                <td class="col-4">Burling / Tuke</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-trap-women"> Trap Women</a></td>
                <td class="col-4">ROONEY Natalie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rugby Sevens</strong></td>
                <td class="col-3"><a href="/en/rugby-sevens-standings-ru-women"> Women</a></td>
                <td class="col-4">New Zealand</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-kayak-k1-women"> Kayak (K1) Women</a></td>
                <td class="col-4">JONES Luuka</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-team-sprint"> Men&#39;s Team Sprint</a></td>
                <td class="col-4">New Zealand</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-pair"> Women&#39;s Pair</a></td>
                <td class="col-4">Behrent / Scown</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-shot-put"> Women&#39;s Shot Put</a></td>
                <td class="col-4">ADAMS Valerie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-470-women"> 470 Women</a></td>
                <td class="col-4">Aleh / Powrie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-49er-fx-women"> 49er FX Women</a></td>
                <td class="col-4">Maloney / Meech</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Golf</strong></td>
                <td class="col-3"><a href="/en/golf-standings-go-womens-individual-stroke-play"> Women&#39;s Individual Stroke Play</a></td>
                <td class="col-4">KO Lydia</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-laser-men"> Laser Men</a></td>
                <td class="col-4">MEECH Sam</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-single-500m"> Women&#39;s Kayak Single 500m</a></td>
                <td class="col-4">CARRINGTON Lisa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-shot-put"> Men&#39;s Shot Put</a></td>
                <td class="col-4">WALSH Tomas</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-pole-vault"> Women&#39;s Pole Vault</a></td>
                <td class="col-4">MCCARTNEY Eliza</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-1500m"> Men&#39;s 1500m</a></td>
                <td class="col-4">WILLIS Nicholas</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-freestyle"> Women&#39;s 100m Freestyle</a></td>
                <td class="col-4">OLEKSIAK Penny</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Trampoline Gymnastics</strong></td>
                <td class="col-3"><a href="/en/trampoline-gymnastics-standings-gt-women"> Women</a></td>
                <td class="col-4">MACLENNAN Rosannagh</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-high-jump"> Men&#39;s High Jump</a></td>
                <td class="col-4">DROUIN Derek</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-75-kg"> Women&#39;s Freestyle 75 kg</a></td>
                <td class="col-4">WIEBE Erica Elizabeth</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-butterfly"> Women&#39;s 100m Butterfly</a></td>
                <td class="col-4">OLEKSIAK Penny</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-womens-double-sculls"> Lightweight Women&#39;s Double Sculls</a></td>
                <td class="col-4">Jennerich / Obee</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-200m"> Men&#39;s 200m</a></td>
                <td class="col-4">DE GRASSE Andre</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-100m-freestyle-relay"> Women&#39;s 4 x 100m Freestyle Relay</a></td>
                <td class="col-4">Canada</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rugby Sevens</strong></td>
                <td class="col-3"><a href="/en/rugby-sevens-standings-ru-women"> Women</a></td>
                <td class="col-4">Canada</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-backstroke"> Women&#39;s 100m Backstroke</a></td>
                <td class="col-4">MASSE Kylie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-synchronised-10m-platform"> Women&#39;s Synchronised 10m Platform</a></td>
                <td class="col-4">Benfeito / Filion</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-200m-freestyle-relay"> Women&#39;s 4 x 200m Freestyle Relay</a></td>
                <td class="col-4">Canada</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-backstroke"> Women&#39;s 200m Backstroke</a></td>
                <td class="col-4">CALDWELL Hilary</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-team-pursuit"> Women&#39;s Team Pursuit</a></td>
                <td class="col-4">Canada</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-heptathlon"> Women&#39;s Heptathlon</a></td>
                <td class="col-4">THEISEN EATON Brianne</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-100m"> Men&#39;s 100m</a></td>
                <td class="col-4">DE GRASSE Andre</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-10m-platform"> Women&#39;s 10m Platform</a></td>
                <td class="col-4">BENFEITO Meaghan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-decathlon"> Men&#39;s Decathlon</a></td>
                <td class="col-4">WARNER Damian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-jumping-individual"> Jumping Individual</a></td>
                <td class="col-4">LAMAZE Eric</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Football</strong></td>
                <td class="col-3"><a href="/en/football-standings-fb-women"> Women</a></td>
                <td class="col-4">Canada</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-4-x-100m-relay"> Men&#39;s 4 x 100m Relay</a></td>
                <td class="col-4">Canada</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Mountain Bike</strong></td>
                <td class="col-3"><a href="/en/cycling-mountain-bike-standings-cm-womens-cross-country"> Women&#39;s Cross-country</a></td>
                <td class="col-4">PENDREL Catharine</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-fly-46-49kg"> Men&#39;s Light Fly (46-49kg)</a></td>
                <td class="col-4">DUSMATOV Hasanboy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-105kg"> Men&#39;s 105kg</a></td>
                <td class="col-4">NURUDINOV Ruslan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-fly-52kg"> Men&#39;s Fly (52kg)</a></td>
                <td class="col-4">ZOIROV Shakhobidin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-welter-64kg"> Men&#39;s Light Welter (64kg)</a></td>
                <td class="col-4">GAIBNAZAROV Fazliddin</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-welter-69kg"> Men&#39;s Welter (69kg)</a></td>
                <td class="col-4">GIYASOV Shakhram</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-middle-75kg"> Men&#39;s Middle (75kg)</a></td>
                <td class="col-4">MELIKUZIEV Bektemir</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-60-kg"> Men -60 kg</a></td>
                <td class="col-4">UROZBOEV Diyorbek</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-66-kg"> Men -66 kg</a></td>
                <td class="col-4">SOBIROV Rishod</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-59-kg"> Men&#39;s Greco-Roman 59 kg</a></td>
                <td class="col-4">TASMURADOV Elmurat</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-heavy-91kg"> Men&#39;s Heavy (91kg)</a></td>
                <td class="col-4">TULAGANOV Rustam</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-bantam-56kg"> Men&#39;s Bantam (56kg)</a></td>
                <td class="col-4">AKHMADALIEV Murodjon</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-65-kg"> Men&#39;s Freestyle 65 kg</a></td>
                <td class="col-4">NAVRUZOV Ikhtiyor</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-97-kg"> Men&#39;s Freestyle 97 kg</a></td>
                <td class="col-4">IBRAGIMOV Magomed Idrisovitch</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-77kg"> Men&#39;s 77kg</a></td>
                <td class="col-4">RAHIMOV Nijat</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-breaststroke"> Men&#39;s 200m Breaststroke</a></td>
                <td class="col-4">BALANDIN Dmitriy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-welter-69kg"> Men&#39;s Welter (69kg)</a></td>
                <td class="col-4">YELEUSSINOV Daniyar</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-60-kg"> Men -60 kg</a></td>
                <td class="col-4">SMETOV Yeldos</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-69kg"> Women&#39;s 69kg</a></td>
                <td class="col-4">ZHAPPARKUL Zhazira</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-heavy-91kg"> Men&#39;s Heavy (91kg)</a></td>
                <td class="col-4">LEVIT Vassiliy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-heavy-81kg"> Men&#39;s Light Heavy (81kg)</a></td>
                <td class="col-4">NIYAZYMBETOV Adilbek</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-75-kg"> Women&#39;s Freestyle 75 kg</a></td>
                <td class="col-4">MANYUROVA Guzel</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-48-kg"> Women -48 kg</a></td>
                <td class="col-4">GALBADRAKH Otgontsetseg</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-62kg"> Men&#39;s 62kg</a></td>
                <td class="col-4">KHARKI Farkhad</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-63kg"> Women&#39;s 63kg</a></td>
                <td class="col-4">GORICHEVA Karina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-triple-jump"> Women&#39;s Triple Jump</a></td>
                <td class="col-4">RYPAKOVA Olga</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-105kg"> Men&#39;s 105kg</a></td>
                <td class="col-4">ZAICHIKOV Alexandr</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-69-kg"> Women&#39;s Freestyle 69 kg</a></td>
                <td class="col-4">SYZDYKOVA Elmira</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-63-kg"> Women&#39;s Freestyle 63 kg</a></td>
                <td class="col-4">LARIONOVA Yekaterina</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-middle-69-75kg"> Women&#39;s Middle (69-75kg)</a></td>
                <td class="col-4">SHAKIMOVA Dariga</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-super-heavy-91kg"> Men&#39;s Super Heavy (+91kg)</a></td>
                <td class="col-4">DYCHKO Ivan</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-62kg"> Men&#39;s 62kg</a></td>
                <td class="col-4">FIGUEROA MOSQUERA Oscar Albeiro</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-triple-jump"> Women&#39;s Triple Jump</a></td>
                <td class="col-4">IBARGUEN Caterine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling BMX</strong></td>
                <td class="col-3"><a href="/en/cycling-bmx-standings-cb-women"> Women</a></td>
                <td class="col-4">PAJON Mariana</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-70-kg"> Women -70 kg</a></td>
                <td class="col-4">ALVEAR Yuri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-fly-46-49kg"> Men&#39;s Light Fly (46-49kg)</a></td>
                <td class="col-4">MARTINEZ Yurberjen Herney</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Cycling BMX</strong></td>
                <td class="col-3"><a href="/en/cycling-bmx-standings-cb-men"> Men</a></td>
                <td class="col-4">RAMIREZ YEPES Carlos Alberto</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-69kg"> Men&#39;s 69kg</a></td>
                <td class="col-4">MOSQUERA LOZANO Luis Javier</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-fly-48-51kg"> Women&#39;s Fly (48-51kg)</a></td>
                <td class="col-4">VALENCIA VICTORIA Ingrit Lorena</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-mens-individual-time-trial"> Men&#39;s Individual Time Trial</a></td>
                <td class="col-4">CANCELLARA Fabian</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-mens-four"> Lightweight Men&#39;s Four</a></td>
                <td class="col-4">Switzerland</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Mountain Bike</strong></td>
                <td class="col-3"><a href="/en/cycling-mountain-bike-standings-cm-mens-cross-country"> Men&#39;s Cross-country</a></td>
                <td class="col-4">SCHURTER Nino</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-womens-doubles"> Women&#39;s Doubles</a></td>
                <td class="col-4">Bacsinszky / Hingis</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Triathlon</strong></td>
                <td class="col-3"><a href="/en/triathlon-standings-tr-women"> Women</a></td>
                <td class="col-4">SPIRIG HUG Nicola</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-25m-pistol-women"> 25m Pistol Women</a></td>
                <td class="col-4">DIETHELM GERBER Heidi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-womens-vault"> Women&#39;s Vault</a></td>
                <td class="col-4">STEINGRUBER Giulia</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-85kg"> Men&#39;s 85kg</a></td>
                <td class="col-4">ROSTAMI Kianoush</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-94kg"> Men&#39;s 94kg</a></td>
                <td class="col-4">MORADI Sohrab</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-74-kg"> Men&#39;s Freestyle 74 kg</a></td>
                <td class="col-4">YAZDANICHARATI Hassan Aliazam</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-125-kg"> Men&#39;s Freestyle 125 kg</a></td>
                <td class="col-4">GHASEMI Komeil Nemat</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-75-kg"> Men&#39;s Greco-Roman 75 kg</a></td>
                <td class="col-4">ABDVALI Saeid Morad</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-98-kg"> Men&#39;s Greco-Roman 98 kg</a></td>
                <td class="col-4">REZAEI Ghasem Gholamreza</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-57kg"> Women -57kg</a></td>
                <td class="col-4">ALIZADEH ZENOORIN Kimia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-57-kg"> Men&#39;s Freestyle 57 kg</a></td>
                <td class="col-4">RAHIMI Hassan Sabzali</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-25m-pistol-women"> 25m Pistol Women</a></td>
                <td class="col-4">KORAKAKI Anna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-rings"> Men&#39;s Rings</a></td>
                <td class="col-4">PETROUNIAS Eleftherios</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-pole-vault"> Women&#39;s Pole Vault</a></td>
                <td class="col-4">STEFANIDI Ekaterini</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Marathon Swimming</strong></td>
                <td class="col-3"><a href="/en/marathon-swimming-standings-ow-mens-10km"> Men&#39;s 10km</a></td>
                <td class="col-4">GIANNIOTIS Spiros</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-pistol-women"> 10m Air Pistol Women</a></td>
                <td class="col-4">KORAKAKI Anna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-470-men"> 470 Men</a></td>
                <td class="col-4">Mantis / Kagialis</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-48-kg"> Women -48 kg</a></td>
                <td class="col-4">PARETO Paula</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-nacra-17-mixed"> Nacra 17 Mixed</a></td>
                <td class="col-4">Lange / Carranza Saroli</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Hockey</strong></td>
                <td class="col-3"><a href="/en/hockey-standings-ho-men"> Men</a></td>
                <td class="col-4">Argentina</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">DEL POTRO Juan Martin</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-50m-freestyle"> Women&#39;s 50m Freestyle</a></td>
                <td class="col-4">BLUME Pernille</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Handball</strong></td>
                <td class="col-3"><a href="/en/handball-standings-hb-men"> Men</a></td>
                <td class="col-4">Denmark</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-mens-road-race"> Men&#39;s Road Race</a></td>
                <td class="col-4">FUGLSANG Jakob</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-mens-four"> Lightweight Men&#39;s Four</a></td>
                <td class="col-4">Denmark</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-75-kg"> Men&#39;s Greco-Roman 75 kg</a></td>
                <td class="col-4">MADSEN Mark Overgaard</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-single-500m"> Women&#39;s Kayak Single 500m</a></td>
                <td class="col-4">JORGENSEN Emma</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-womens-doubles"> Women&#39;s Doubles</a></td>
                <td class="col-4">Rytter juhl / Pedersen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-400m-hurdles"> Women&#39;s 400m Hurdles</a></td>
                <td class="col-4">PETERSEN Sara Slott</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-pair"> Women&#39;s Pair</a></td>
                <td class="col-4">Andersen / Rasmussen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-team-pursuit"> Men&#39;s Team Pursuit</a></td>
                <td class="col-4">Denmark</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-4-x-100m-medley-relay"> Women&#39;s 4 x 100m Medley Relay</a></td>
                <td class="col-4">Denmark</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-omnium"> Men&#39;s Omnium</a></td>
                <td class="col-4">HANSEN Lasse Norman</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-laser-radial-women"> Laser Radial Women</a></td>
                <td class="col-4">RINDOM Anne-Marie</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-49er-fx-women"> 49er FX Women</a></td>
                <td class="col-4">Salskov-Iversen / Hansen</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">AXELSEN Viktor</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-butterfly"> Women&#39;s 100m Butterfly</a></td>
                <td class="col-4">SJOSTROM Sarah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Mountain Bike</strong></td>
                <td class="col-3"><a href="/en/cycling-mountain-bike-standings-cm-womens-cross-country"> Women&#39;s Cross-country</a></td>
                <td class="col-4">RISSVEDS Jenny</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-womens-road-race"> Women&#39;s Road Race</a></td>
                <td class="col-4">JOHANSSON Emma</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-200m-freestyle"> Women&#39;s 200m Freestyle</a></td>
                <td class="col-4">SJOSTROM Sarah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-skeet-men"> Skeet Men</a></td>
                <td class="col-4">SVENSSON Marcus</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Golf</strong></td>
                <td class="col-3"><a href="/en/golf-standings-go-mens-individual-stroke-play"> Men&#39;s Individual Stroke Play</a></td>
                <td class="col-4">STENSON Henrik</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Equestrian</strong></td>
                <td class="col-3"><a href="/en/equestrian-standings-eq-jumping-individual"> Jumping Individual</a></td>
                <td class="col-4">FREDRICSON Peder</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Football</strong></td>
                <td class="col-3"><a href="/en/football-standings-fb-women"> Women</a></td>
                <td class="col-4">Sweden</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-100m-freestyle"> Women&#39;s 100m Freestyle</a></td>
                <td class="col-4">SJOSTROM Sarah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-69-kg"> Women&#39;s Freestyle 69 kg</a></td>
                <td class="col-4">FRANSSON Anna Jenny</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-53-kg"> Women&#39;s Freestyle 53 kg</a></td>
                <td class="col-4">MATTSSON Sofia Magdalena</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-400m"> Men&#39;s 400m</a></td>
                <td class="col-4">VAN NIEKERK Wayde</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-800m"> Women&#39;s 800m</a></td>
                <td class="col-4">SEMENYA Caster</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-breaststroke"> Men&#39;s 100m Breaststroke</a></td>
                <td class="col-4">VAN DER BURGH Cameron</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-200m-freestyle"> Men&#39;s 200m Freestyle</a></td>
                <td class="col-4">LE CLOS Chad Guy Bertrand</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-pair"> Men&#39;s Pair</a></td>
                <td class="col-4">Keeling / Brittain</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-butterfly"> Men&#39;s 100m Butterfly</a></td>
                <td class="col-4">LE CLOS Chad Guy Bertrand</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-long-jump"> Men&#39;s Long Jump</a></td>
                <td class="col-4">MANYONGA Luvo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-javelin-throw"> Women&#39;s Javelin Throw</a></td>
                <td class="col-4">VILJOEN Sunette</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Rugby Sevens</strong></td>
                <td class="col-3"><a href="/en/rugby-sevens-standings-ru-men"> Men</a></td>
                <td class="col-4">South Africa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Triathlon</strong></td>
                <td class="col-3"><a href="/en/triathlon-standings-tr-men"> Men</a></td>
                <td class="col-4">SCHOEMAN Henri</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-parallel-bars"> Men&#39;s Parallel Bars</a></td>
                <td class="col-4">VERNIAIEV Oleg</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-single-200m"> Men&#39;s Canoe Single 200m</a></td>
                <td class="col-4">CHEBAN Iurii</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-rifle-men"> 10m Air Rifle Men</a></td>
                <td class="col-4">KULISH Serhiy</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-individual-all-around"> Men&#39;s Individual All-Around</a></td>
                <td class="col-4">VERNIAIEV Oleg</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-sabre-team"> Women&#39;s Sabre Team</a></td>
                <td class="col-4">Ukraine</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-85-kg"> Men&#39;s Greco-Roman 85 kg</a></td>
                <td class="col-4">BELENIUK Zhan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Modern Pentathlon</strong></td>
                <td class="col-3"><a href="/en/modern-pentathlon-standings-mp-mens-individual"> Men&#39;s Individual</a></td>
                <td class="col-4">TYMOSHCHENKO Pavlo</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-sabre-individual"> Women&#39;s Sabre Individual</a></td>
                <td class="col-4">KHARLAN Olga</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-high-jump"> Men&#39;s High Jump</a></td>
                <td class="col-4">BONDARENKO Bohdan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-double-1000m"> Men&#39;s Canoe Double 1000m</a></td>
                <td class="col-4">Ianchuk / Mishchuk</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rhythmic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/rhythmic-gymnastics-standings-gr-individual-all-around"> Individual All-Around</a></td>
                <td class="col-4">RIZATDINOVA Ganna</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-66-kg"> Men&#39;s Greco-Roman 66 kg</a></td>
                <td class="col-4">STEFANEK Davor</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Water Polo</strong></td>
                <td class="col-3"><a href="/en/water-polo-standings-wp-men"> Men</a></td>
                <td class="col-4">Serbia</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-49kg"> Women -49kg</a></td>
                <td class="col-4">BOGDANOVIC Tijana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-double-1000m"> Men&#39;s Kayak Double 1000m</a></td>
                <td class="col-4">Tomicevic / Zoric</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Volleyball</strong></td>
                <td class="col-3"><a href="/en/volleyball-standings-vo-women"> Women</a></td>
                <td class="col-4">Serbia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Basketball</strong></td>
                <td class="col-3"><a href="/en/basketball-standings-bk-men"> Men</a></td>
                <td class="col-4">Serbia</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-long-jump"> Women&#39;s Long Jump</a></td>
                <td class="col-4">SPANOVIC Ivana</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Basketball</strong></td>
                <td class="col-3"><a href="/en/basketball-standings-bk-women"> Women</a></td>
                <td class="col-4">Serbia</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-double-sculls"> Women&#39;s Double Sculls</a></td>
                <td class="col-4">Fularczyk-Kozlowska / Madaj</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-hammer-throw"> Women&#39;s Hammer Throw</a></td>
                <td class="col-4">WLODARCZYK Anita</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-discus-throw"> Men&#39;s Discus Throw</a></td>
                <td class="col-4">MALACHOWSKI Piotr</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-single-200m"> Women&#39;s Kayak Single 200m</a></td>
                <td class="col-4">WALCZYKIEWICZ Marta</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Mountain Bike</strong></td>
                <td class="col-3"><a href="/en/cycling-mountain-bike-standings-cm-womens-cross-country"> Women&#39;s Cross-country</a></td>
                <td class="col-4">WLOSZCZOWSKA Maja</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-mens-road-race"> Men&#39;s Road Race</a></td>
                <td class="col-4">MAJKA Rafal</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-quadruple-sculls"> Women&#39;s Quadruple Sculls</a></td>
                <td class="col-4">Poland</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-double-500m"> Women&#39;s Kayak Double 500m</a></td>
                <td class="col-4">Mikolajczyk / Naja</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-63-kg"> Women&#39;s Freestyle 63 kg</a></td>
                <td class="col-4">MICHALIK Monika Ewa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Modern Pentathlon</strong></td>
                <td class="col-3"><a href="/en/modern-pentathlon-standings-mp-womens-individual"> Women&#39;s Individual</a></td>
                <td class="col-4">NOWACKA Oktawia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-hammer-throw"> Men&#39;s Hammer Throw</a></td>
                <td class="col-4">NOWICKI Wojciech</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-75kg-2"> Women&#39;s 75kg</a></td>
                <td class="col-4">RIM Jong Sim</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Artistic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/artistic-gymnastics-standings-ga-mens-vault"> Men&#39;s Vault</a></td>
                <td class="col-4">RI Se Gwang</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-56kg"> Men&#39;s 56kg</a></td>
                <td class="col-4">OM Yun Chol</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-63kg"> Women&#39;s 63kg</a></td>
                <td class="col-4">CHOE Hyo Sim</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-75kg"> Women&#39;s +75kg</a></td>
                <td class="col-4">KIM Kuk Hyang</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-pistol-men"> 50m Pistol Men</a></td>
                <td class="col-4">KIM Song Guk</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Table Tennis</strong></td>
                <td class="col-3"><a href="/en/table-tennis-standings-tt-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">KIM Song I</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Cycling Road</strong></td>
                <td class="col-3"><a href="/en/cycling-road-standings-cr-mens-road-race"> Men&#39;s Road Race</a></td>
                <td class="col-4">VAN AVERMAET Greg</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-heptathlon"> Women&#39;s Heptathlon</a></td>
                <td class="col-4">THIAM Nafissatou</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-freestyle"> Men&#39;s 100m Freestyle</a></td>
                <td class="col-4">TIMMERS Pieter</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Hockey</strong></td>
                <td class="col-3"><a href="/en/hockey-standings-ho-men"> Men</a></td>
                <td class="col-4">Belgium</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-73-kg"> Men -73 kg</a></td>
                <td class="col-4">VAN TICHELT Dirk</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-womens-omnium"> Women&#39;s Omnium</a></td>
                <td class="col-4">D&#39;HOORE Jolien</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-48kg"> Women&#39;s 48kg</a></td>
                <td class="col-4">TANASAN Sopita</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-58kg"> Women&#39;s 58kg</a></td>
                <td class="col-4">SRISURAT Sukanya</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-58kg"> Women&#39;s 58kg</a></td>
                <td class="col-4">SIRIKAEW Pimsiri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-58kg"> Men -58kg</a></td>
                <td class="col-4">HANPRAB Tawin</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-56kg"> Men&#39;s 56kg</a></td>
                <td class="col-4">KRUAITHONG Sinphet</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-49kg"> Women -49kg</a></td>
                <td class="col-4">WONGPATTANAKIT Panipak</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-canoe-double-c2-men"> Canoe Double (C2) Men</a></td>
                <td class="col-4">Skantar / Skantar</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-50km-walk"> Men&#39;s 50km Walk</a></td>
                <td class="col-4">TOTH Matej</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-canoe-single-c1-men"> Canoe Single (C1) Men</a></td>
                <td class="col-4">BENUS Matej</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-four-1000m"> Men&#39;s Kayak Four 1000m</a></td>
                <td class="col-4">Slovakia</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-105kg-2"> Men&#39;s +105kg</a></td>
                <td class="col-4">TALAKHADZE Lasha</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-57-kg"> Men&#39;s Freestyle 57 kg</a></td>
                <td class="col-4">KHINCHEGASHVILI Vladimer</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-90-kg"> Men -90 kg</a></td>
                <td class="col-4">LIPARTELIANI Varlam</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-73-kg"> Men -73 kg</a></td>
                <td class="col-4">SHAVDATUASHVILI Lasha</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-66-kg"> Men&#39;s Greco-Roman 66 kg</a></td>
                <td class="col-4">BOLKVADZE Shmagi</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-105kg-2"> Men&#39;s +105kg</a></td>
                <td class="col-4">TURMANIDZE Irakli</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-125-kg"> Men&#39;s Freestyle 125 kg</a></td>
                <td class="col-4">PETRIASHVILI Geno</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg-2"> Men +80kg</a></td>
                <td class="col-4">ISAEV Radik</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-73-kg"> Men -73 kg</a></td>
                <td class="col-4">ORUJOV Rustam</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg"> Men -100 kg</a></td>
                <td class="col-4">GASIMOV Elmar</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-48-kg"> Women&#39;s Freestyle 48 kg</a></td>
                <td class="col-4">STADNIK Mariya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-single-200m"> Men&#39;s Canoe Single 200m</a></td>
                <td class="col-4">DEMYANENKO Valentin</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-65-kg"> Men&#39;s Freestyle 65 kg</a></td>
                <td class="col-4">ASGAROV Toghrul</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-welter-64kg"> Men&#39;s Light Welter (64kg)</a></td>
                <td class="col-4">SOTOMAYOR COLLAZO Lorenzo</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-97-kg"> Men&#39;s Freestyle 97 kg</a></td>
                <td class="col-4">GOZIUMOV Khetag</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-130-kg"> Men&#39;s Greco-Roman 130 kg</a></td>
                <td class="col-4">SHARIATI Sabah</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-single-200m"> Women&#39;s Kayak Single 200m</a></td>
                <td class="col-4">OSIPENKO-RODOMSKA Inna</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-66-kg"> Men&#39;s Greco-Roman 66 kg</a></td>
                <td class="col-4">CHUNAYEV Rasul</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-49kg"> Women -49kg</a></td>
                <td class="col-4">ABAKAROVA Patimat</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-53-kg"> Women&#39;s Freestyle 53 kg</a></td>
                <td class="col-4">SINISHIN Natalya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-57-kg"> Men&#39;s Freestyle 57 kg</a></td>
                <td class="col-4">ALIYEV Haji</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-74-kg"> Men&#39;s Freestyle 74 kg</a></td>
                <td class="col-4">HASANOV Jabrayil</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg"> Men -80kg</a></td>
                <td class="col-4">BEIGI HARCHEGANI Milad</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-middle-75kg"> Men&#39;s Middle (75kg)</a></td>
                <td class="col-4">SHAKHSUVARLY Kamran</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-86-kg"> Men&#39;s Freestyle 86 kg</a></td>
                <td class="col-4">SHARIFOV Sharif</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Trampoline Gymnastics</strong></td>
                <td class="col-3"><a href="/en/trampoline-gymnastics-standings-gt-men"> Men</a></td>
                <td class="col-4">HANCHAROU Uladzislau</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-75kg-2"> Women&#39;s 75kg</a></td>
                <td class="col-4">NAUMAVA Darya</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-94kg"> Men&#39;s 94kg</a></td>
                <td class="col-4">STRALTSOU Vadzim</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-63-kg"> Women&#39;s Freestyle 63 kg</a></td>
                <td class="col-4">MAMASHUK Maryia</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-hammer-throw"> Men&#39;s Hammer Throw</a></td>
                <td class="col-4">TSIKHAN Ivan</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-womens-50m-freestyle"> Women&#39;s 50m Freestyle</a></td>
                <td class="col-4">HERASIMENIA Aliaksandra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-85-kg"> Men&#39;s Greco-Roman 85 kg</a></td>
                <td class="col-4">HAMZATAU Javid</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-womens-kayak-four-500m"> Women&#39;s Kayak Four 500m</a></td>
                <td class="col-4">Belarus</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-125-kg"> Men&#39;s Freestyle 125 kg</a></td>
                <td class="col-4">SAIDAU Ibrahim</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-125-kg"> Men&#39;s Freestyle 125 kg</a></td>
                <td class="col-4">AKGUL Taha</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-130-kg"> Men&#39;s Greco-Roman 130 kg</a></td>
                <td class="col-4">KAYAALP Riza</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-69kg"> Men&#39;s 69kg</a></td>
                <td class="col-4">ISMAYILOV Daniyar</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-86-kg"> Men&#39;s Freestyle 86 kg</a></td>
                <td class="col-4">YASAR Selim</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-98-kg"> Men&#39;s Greco-Roman 98 kg</a></td>
                <td class="col-4">ILDEM Cenk</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-400m-hurdles"> Men&#39;s 400m Hurdles</a></td>
                <td class="col-4">COPELLO Yasmani</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-74-kg"> Men&#39;s Freestyle 74 kg</a></td>
                <td class="col-4">DEMIRTAS Soner</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg"> Women -67kg</a></td>
                <td class="col-4">TATAR Nur</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-98-kg"> Men&#39;s Greco-Roman 98 kg</a></td>
                <td class="col-4">ALEKSANYAN Artur</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-105kg"> Men&#39;s 105kg</a></td>
                <td class="col-4">MARTIROSYAN Simon</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-66-kg"> Men&#39;s Greco-Roman 66 kg</a></td>
                <td class="col-4">ARUTYUNYAN Migran</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-105kg-2"> Men&#39;s +105kg</a></td>
                <td class="col-4">MINASYAN Gor</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg"> Men -100 kg</a></td>
                <td class="col-4">KRPALEK Lukas</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-single-1000m"> Men&#39;s Kayak Single 1000m</a></td>
                <td class="col-4">DOSTAL Josef</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Cycling Mountain Bike</strong></td>
                <td class="col-3"><a href="/en/cycling-mountain-bike-standings-cm-mens-cross-country"> Men&#39;s Cross-country</a></td>
                <td class="col-4">KULHAVY Jaroslav</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-kayak-k1-men"> Kayak (K1) Men</a></td>
                <td class="col-4">PRSKAVEC Jiri</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-single-sculls"> Men&#39;s Single Sculls</a></td>
                <td class="col-4">SYNEK Ondrej</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">KVITOVA Petra</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-womens-doubles"> Women&#39;s Doubles</a></td>
                <td class="col-4">Safarova / Strycova</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mixed-doubles"> Mixed Doubles</a></td>
                <td class="col-4">Hradecka / Stepanek</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-javelin-throw"> Women&#39;s Javelin Throw</a></td>
                <td class="col-4">SPOTAKOVA Barbora</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-four-1000m"> Men&#39;s Kayak Four 1000m</a></td>
                <td class="col-4">Czech Republic</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-10-000m"> Women&#39;s 10,000m</a></td>
                <td class="col-4">AYANA Almaz</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-1500m"> Women&#39;s 1500m</a></td>
                <td class="col-4">DIBABA Genzebe</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-marathon"> Men&#39;s Marathon</a></td>
                <td class="col-4">LILESA Feyisa</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-10-000m"> Women&#39;s 10,000m</a></td>
                <td class="col-4">DIBABA Tirunesh</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-10-000m"> Men&#39;s 10,000m</a></td>
                <td class="col-4">TOLA Tamirat</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-marathon"> Women&#39;s Marathon</a></td>
                <td class="col-4">DIBABA Mare</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-5000m"> Women&#39;s 5000m</a></td>
                <td class="col-4">AYANA Almaz</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-5000m"> Men&#39;s 5000m</a></td>
                <td class="col-4">GEBRHIWET Hagos</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-63-kg"> Women -63 kg</a></td>
                <td class="col-4">TRSTENJAK Tina</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Canoe Slalom</strong></td>
                <td class="col-3"><a href="/en/canoe-slalom-standings-cs-kayak-k1-men"> Kayak (K1) Men</a></td>
                <td class="col-4">KAUZER Peter</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-finn-men"> Finn Men</a></td>
                <td class="col-4">ZBOGAR Vasilij</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-78-kg"> Women -78 kg</a></td>
                <td class="col-4">VELENSEK Anamari</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mixed-doubles"> Mixed Doubles</a></td>
                <td class="col-4">Natsir / Ahmad</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-48kg"> Women&#39;s 48kg</a></td>
                <td class="col-4">AGUSTIANI Sri Wahyuni</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-62kg"> Men&#39;s 62kg</a></td>
                <td class="col-4">IRAWAN Eko Yuli</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-epee-team"> Women&#39;s &#201;p&#233;e Team</a></td>
                <td class="col-4">Romania</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-mens-doubles"> Men&#39;s Doubles</a></td>
                <td class="col-4">Mergea / Tecau</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-85kg"> Men&#39;s 85kg</a></td>
                <td class="col-4">SINCRAIAN Gabriel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-eight"> Women&#39;s Eight</a></td>
                <td class="col-4">Romania</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-freestyle-97-kg"> Men&#39;s Freestyle 97 kg</a></td>
                <td class="col-4">SARITOV Albert</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-3000m-steeplechase"> Women&#39;s 3000m Steeplechase</a></td>
                <td class="col-4">JEBET Ruth</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-marathon"> Women&#39;s Marathon</a></td>
                <td class="col-4">KIRWA Eunice Jepkirui</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-10m-air-pistol-men"> 10m Air Pistol Men</a></td>
                <td class="col-4">HOANG Xuan Vinh</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-50m-pistol-men"> 50m Pistol Men</a></td>
                <td class="col-4">HOANG Xuan Vinh</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-53kg"> Women&#39;s 53kg</a></td>
                <td class="col-4">HSU Shu-Ching</td>
            </tr>

                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Archery</strong></td>
                <td class="col-3"><a href="/en/archery-standings-ar-womens-team"> Women&#39;s Team</a></td>
                <td class="col-4">Taipei (Cn Taipei)</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-58kg"> Women&#39;s 58kg</a></td>
                <td class="col-4">KUO Hsing-Chun</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-400m"> Women&#39;s 400m</a></td>
                <td class="col-4">MILLER Shaunae</td>
            </tr>

                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-4-x-400m-relay"> Men&#39;s 4 x 400m Relay</a></td>
                <td class="col-4">Bahamas</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg"> Men -80kg</a></td>
                <td class="col-4">CISSE Cheick Sallah Junior</td>
            </tr>

                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg"> Women -67kg</a></td>
                <td class="col-4">GBAGBI Ruth Marie Christelle</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-double-trap-men"> Double Trap Men</a></td>
                <td class="col-4">ALDEEHANI Fehaid</td>
            </tr>

                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Shooting</strong></td>
                <td class="col-3"><a href="/en/shooting-standings-sh-skeet-men"> Skeet Men</a></td>
                <td class="col-4">ALRASHIDI Abdullah</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Rugby Sevens</strong></td>
                <td class="col-3"><a href="/en/rugby-sevens-standings-ru-men"> Men</a></td>
                <td class="col-4">Fiji</td>
            </tr>

                                        
                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-68kg"> Men -68kg</a></td>
                <td class="col-4">ABUGHAUSH Ahmad</td>
            </tr>

                                        
                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-52-kg"> Women -52 kg</a></td>
                <td class="col-4">KELMENDI Majlinda</td>
            </tr>

                                        
                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Tennis</strong></td>
                <td class="col-3"><a href="/en/tennis-standings-te-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">PUIG Monica</td>
            </tr>

                                        
                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Swimming</strong></td>
                <td class="col-3"><a href="/en/swimming-standings-sw-mens-100m-butterfly"> Men&#39;s 100m Butterfly</a></td>
                <td class="col-4">SCHOOLING Joseph</td>
            </tr>

                                        
                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-gold-typed" aria-label="Gold" title="Gold">Gold</span>
                        <span class="medal-name">Gold</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-hammer-throw"> Men&#39;s Hammer Throw</a></td>
                <td class="col-4">NAZAROV Dilshod</td>
            </tr>

                                        
                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-womens-synchronised-10m-platform"> Women&#39;s Synchronised 10m Platform</a></td>
                <td class="col-4">Cheong / Pamg</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mixed-doubles"> Mixed Doubles</a></td>
                <td class="col-4">Chan / Goh</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mens-doubles"> Men&#39;s Doubles</a></td>
                <td class="col-4">Goh / Tan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-mens-singles"> Men&#39;s Singles</a></td>
                <td class="col-4">LEE Chong Wei</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Cycling Track</strong></td>
                <td class="col-3"><a href="/en/cycling-track-standings-ct-mens-keirin"> Men&#39;s Keirin</a></td>
                <td class="col-4">AWANG Azizulhasni</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-20km-walk"> Women&#39;s 20km Walk</a></td>
                <td class="col-4">GONZALEZ Maria Guadalupe</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Diving</strong></td>
                <td class="col-3"><a href="/en/diving-standings-dv-mens-10m-platform"> Men&#39;s 10m Platform</a></td>
                <td class="col-4">SANCHEZ German</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-67kg-2"> Women +67kg</a></td>
                <td class="col-4">ESPINOZA ESPINOZA Maria del Rosario</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-middle-75kg"> Men&#39;s Middle (75kg)</a></td>
                <td class="col-4">RODRIGUEZ Misael Uziel</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Modern Pentathlon</strong></td>
                <td class="col-3"><a href="/en/modern-pentathlon-standings-mp-mens-individual"> Men&#39;s Individual</a></td>
                <td class="col-4">HERNANDEZ USCANGA Ismael Marcelo</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-800m"> Men&#39;s 800m</a></td>
                <td class="col-4">MAKHLOUFI Taoufik</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-1500m"> Men&#39;s 1500m</a></td>
                <td class="col-4">MAKHLOUFI Taoufik</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-mens-double-sculls"> Lightweight Men&#39;s Double Sculls</a></td>
                <td class="col-4">O&#39;Donovan / O&#39;Donovan</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-laser-radial-women"> Laser Radial Women</a></td>
                <td class="col-4">MURPHY Annalise</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-double-sculls"> Men&#39;s Double Sculls</a></td>
                <td class="col-4">Griskonis / Ritter</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-womens-double-sculls"> Women&#39;s Double Sculls</a></td>
                <td class="col-4">Vistartaite / Valciukaite</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-94kg"> Men&#39;s 94kg</a></td>
                <td class="col-4">DIDZBALIS Aurimas</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-kayak-double-200m"> Men&#39;s Kayak Double 200m</a></td>
                <td class="col-4">Ramanauskas / Lankas</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-high-jump"> Women&#39;s High Jump</a></td>
                <td class="col-4">DEMIREVA Mirela</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-48-kg"> Women&#39;s Freestyle 48 kg</a></td>
                <td class="col-4">YANKOVA Elitsa Atanasova</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rhythmic Gymnastics</strong></td>
                <td class="col-3"><a href="/en/rhythmic-gymnastics-standings-gr-group-all-around"> Group All-Around</a></td>
                <td class="col-4">Bulgaria</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-triple-jump"> Women&#39;s Triple Jump</a></td>
                <td class="col-4">ROJAS Yulimar</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Cycling BMX</strong></td>
                <td class="col-3"><a href="/en/cycling-bmx-standings-cb-women"> Women</a></td>
                <td class="col-4">HERNANDEZ Stefany</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-fly-52kg"> Men&#39;s Fly (52kg)</a></td>
                <td class="col-4">FINOL Yoel Segundo</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Badminton</strong></td>
                <td class="col-3"><a href="/en/badminton-standings-bd-womens-singles"> Women&#39;s Singles</a></td>
                <td class="col-4">PUSARLA V. Sindhu</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-58-kg"> Women&#39;s Freestyle 58 kg</a></td>
                <td class="col-4">MALIK Sakshi</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-57-kg"> Women -57 kg</a></td>
                <td class="col-4">DORJSUREN Sumiya</td>
            </tr>

                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-light-60kg"> Men&#39;s Light (60kg)</a></td>
                <td class="col-4">DORJNYAMBUU Otgondalai</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-womens-800m"> Women&#39;s 800m</a></td>
                <td class="col-4">NIYONSABA Francine</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-400m"> Men&#39;s 400m</a></td>
                <td class="col-4">JAMES Kirani</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg-2"> Men +80kg</a></td>
                <td class="col-4">ISSOUFOU ALFAGA Abdoulrazak</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-53kg"> Women&#39;s 53kg</a></td>
                <td class="col-4">DIAZ Hidilyn</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-silver-typed" aria-label="Silver" title="Silver">Silver</span>
                        <span class="medal-name">Silver</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-high-jump"> Men&#39;s High Jump</a></td>
                <td class="col-4">BARSHIM Mutaz Essa</td>
            </tr>

                                        
                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-double-sculls"> Men&#39;s Double Sculls</a></td>
                <td class="col-4">Tufte / Borch</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-lightweight-mens-double-sculls"> Lightweight Men&#39;s Double Sculls</a></td>
                <td class="col-4">Strandli / Brun</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-mens-greco-roman-59-kg"> Men&#39;s Greco-Roman 59 kg</a></td>
                <td class="col-4">BERGE Stig-Andre</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Handball</strong></td>
                <td class="col-3"><a href="/en/handball-standings-hb-women"> Women</a></td>
                <td class="col-4">Norway</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-womens-69kg"> Women&#39;s 69kg</a></td>
                <td class="col-4">AHMED Sara</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Weightlifting</strong></td>
                <td class="col-3"><a href="/en/weightlifting-standings-wl-mens-77kg"> Men&#39;s 77kg</a></td>
                <td class="col-4">MAHMOUD Mohamed</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-women-57kg"> Women -57kg</a></td>
                <td class="col-4">WAHBA Hedaya</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Fencing</strong></td>
                <td class="col-3"><a href="/en/fencing-standings-fe-womens-foil-individual"> Women&#39;s Foil Individual</a></td>
                <td class="col-4">BOUBAKRI Ines</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Wrestling</strong></td>
                <td class="col-3"><a href="/en/wrestling-standings-wr-womens-freestyle-58-kg"> Women&#39;s Freestyle 58 kg</a></td>
                <td class="col-4">AMRI Marwa</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-80kg"> Men -80kg</a></td>
                <td class="col-4">OUESLATI Oussama</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-63-kg"> Women -63 kg</a></td>
                <td class="col-4">GERBI Yarden</td>
            </tr>
            <tr>
                <td class="col-1">
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-100-kg-2"> Men +100 kg</a></td>
                <td class="col-4">SASSON Or</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Sailing</strong></td>
                <td class="col-3"><a href="/en/sailing-standings-sa-nacra-17-mixed"> Nacra 17 Mixed</a></td>
                <td class="col-4">Frank / Zajac</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Taekwondo</strong></td>
                <td class="col-3"><a href="/en/taekwondo-standings-tk-men-58kg"> Men -58kg</a></td>
                <td class="col-4">PIE Luisito</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Rowing</strong></td>
                <td class="col-3"><a href="/en/rowing-standings-ro-mens-quadruple-sculls"> Men&#39;s Quadruple Sculls</a></td>
                <td class="col-4">Estonia</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-womens-light-57-60kg"> Women&#39;s Light (57-60kg)</a></td>
                <td class="col-4">POTKONEN Mira</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Boxing</strong></td>
                <td class="col-3"><a href="/en/boxing-standings-bx-mens-welter-69kg"> Men&#39;s Welter (69kg)</a></td>
                <td class="col-4">RABII Mohammed</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Canoe Sprint</strong></td>
                <td class="col-3"><a href="/en/canoe-sprint-standings-cf-mens-canoe-single-1000m"> Men&#39;s Canoe Single 1000m</a></td>
                <td class="col-4">TARNOVSCHI Serghei</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Football</strong></td>
                <td class="col-3"><a href="/en/football-standings-fb-men"> Men</a></td>
                <td class="col-4">Nigeria</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-women-57-kg"> Women -57 kg</a></td>
                <td class="col-4">MONTEIRO Telma</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Athletics</strong></td>
                <td class="col-3"><a href="/en/athletics-standings-at-mens-javelin-throw"> Men&#39;s Javelin Throw</a></td>
                <td class="col-4">WALCOTT Keshorn</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
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
                        <tr class="table-expand">
                            <td colspan="100%">
                                <table class="table-medals">
                                    <tbody>
                                        
                                        
                                                    <tr class="type">
                <td class="col-1">
                        <span class="medal sprite-ui sprite-ui--medal-bronze-typed" aria-label="Bronze" title="Bronze">Bronze</span>
                        <span class="medal-name">Bronze</span>
                </td>
                <td class="col-2"><strong>Judo</strong></td>
                <td class="col-3"><a href="/en/judo-standings-ju-men-81-kg"> Men -81 kg</a></td>
                <td class="col-4">TOMA Sergiu</td>
            </tr>

                                    </tbody>
                                </table>
                            </td>
                        </tr>
			</tbody>
		</table>
	</div>        </small>
    </jsp:attribute> 
</idisc:page_with_slider>

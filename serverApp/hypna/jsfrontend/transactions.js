var data = {
    "payments": [
        {
            "uuid": "D100001",
            "trStDate": "01/08/2013",
            "trEnDate": "01/08/2013",
            "trStatus": "Complete",
            "amount": 23500.00,
            "cardholder": "Nuwan Waidyanatha",
            "cardno": "1234 5678 1234",
            "phone": "773710394",
            "IMEI": "1157234567"
        },
        {
            "uuid": "D100002",
            "trStDate": "01/01/2013",
            "trEnDate": "02/05/2013",
            "trStatus": "Pending",
            "amount": 23500.00,
            "cardholder": "Gehan Dias",
            "cardno": "1234 5678 1234",
            "phone": "713710394",
            "IMEI": "2510543468"
        },
        {
            "uuid": "D100002",
            "trStDate": "01/05/2013",
            "trEnDate": "01/12/2013",
            "trStatus": "Pending",
            "amount": 23500.00,
            "cardholder": "Madhuka Udantha",
            "cardno": "1234 5678 1234",
            "phone": "723710394",
            "IMEI": "90217843675"
        }
    ]
}

function clear(){
    $('#results').hide();
};

function search(status, stdate, enddate, location){
    var found = 0;
    var output = "";
    
    $('#results').show();
    $('#mapdiv').hide();
    
    output += "<table><thead><tr><td></td><td></td>";
    output += "<td><h4>Unique ID</h4></td>";
    output += "<td><h4>Initiated On</h4></td>";
    output += "<td><h4>Completed By</h4></td>";
    output += "<td><h4>Cardholder</h4></td>";
    output += "<td><h4>Card No.</h4></td>";
    output += "<td><h4>Phone No.</h4></td>";
    output += "<td><h4>IMEI</h4></td></tr></thead>";

    for (var i in data.payments){
        var re_stdate = new RegExp(stdate, "i");
        var re_enddate = new RegExp(enddate, "i");
        var re_location = new RegExp(location, "i");
        if (data.payments[i].trStDate.search(re_stdate) != -1 
            && data.payments[i].trEnDate.search(re_enddate) != -1 
            && data.payments[i].phone.search(re_location) != -1)
            {
            output += "<tr><td><input type='button' value='Edit' id='edit'" + data.payments[i].uuid + " /></td>";
            output += "<td><input type='button' value='Delete' id='delete'" + data.payments[i].uuid + " /></td>";
            output += "<td><p>" + data.payments[i].uuid +"</p></td>";
            output += "<td><p>" + data.payments[i].trStDate +"</p></td>";
            output += "<td><p>" + data.payments[i].trEnDate +"</p></td>";
            output += "<td><p>" + data.payments[i].cardholder +"</p></td>";
            output +=  "<td><p>" + data.payments[i].cardno +"</p></td>";
            output +=  "<td><p>" + data.payments[i].phone +"</p></td>";
            output +=  "<td><p>" + data.payments[i].IMEI +"</p></td>";
            output += "</tr>";
            found +=1;
            }
    }
    output += "</table>";
    if (found==0){output = "<h4>Refine your search terms, NO matching records were found</h4>";}
    else { output = "<h2>" + found + " RESULTS </h2>" + output; }
    document.getElementById("results").innerHTML=output;
};

function tran_by_loc(status, startdate, enddate, location){

    var lonctr = 80.414703;
    var latctr = 8.3147;
    map = new OpenLayers.Map("mapdiv");
    map.addLayer(new OpenLayers.Layer.OSM());
 
    var pois = new OpenLayers.Layer.Text( "My Points",
                    { location:"./data/tran_by_loc.txt",
                      projection: map.displayProjection
                    });
    map.addLayer(pois);
 
    //Set start centrepoint and zoom    
    var lonLat = new OpenLayers.LonLat( lonctr, latctr )
          .transform(
            new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
            map.getProjectionObject() // to Spherical Mercator Projection
          );
    var zoom=7;
    map.setCenter (lonLat, zoom);
};

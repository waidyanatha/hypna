function clear(){
    $('#results').hide();
};

function search(fname, lname, phone){
    $.getJSON('../pos/data/payees.json', function(data) {
    var found = 0;
    var output = "";

    $('#results').show();
    output += "<table><thead><tr><td></td><td></td>";
    output += "<td><h4>Unique ID</h4></td>";
    output += "<td><h4>First Name</h4></td>";
    output += "<td><h4>Surname</h4></td>";
    output += "<td><h4>Phone No.</h4></td>";
    output += "<td><h4>IMEI</h4></td></tr></thead>";

        for (var i in data.payees)
            {
            var re_fname = new RegExp(fname, "i");
            var re_lname = new RegExp(lname, "i");
            var re_phone = new RegExp(phone, "i");
            if (data.payees[i].fname.search(re_fname) != -1
                && data.payees[i].lname.search(re_lname) != -1
                && data.payees[i].phone.search(re_phone) != -1)
                {
                output += "<tr><td><input type='button' value='Edit' id='edit'" + data.payees[i].uuid + " /></td>";
                output += "<td><input type='button' value='Delete' id='delete'" + data.payees[i].uuid + " /></td>";
                output += "<td><p>" + data.payees[i].uuid +"</p></td>";
                output += "<td><p>" + data.payees[i].fname +"</p></td>";
                output +=  "<td><p>" + data.payees[i].lname +"</p></td>";
                output +=  "<td><p>" + data.payees[i].phone +"</p></td>";
                output +=  "<td><p>" + data.payees[i].IMEI +"</p></td>";
                output += "</tr>";
                found +=1;
                }
            }
    output += "</table>";
    if (found==0){output = "<h4>Refine your search terms, NO matching records were found</h4>";}
    else { output = "<h2>" + found + " RESULTS </h2>" + output; }
    document.getElementById("results").innerHTML=output;
        });
};

function add(){
    $('#results').hide();
    $('.box').toggle();
};

function save(fname, lname, phone){
    var found = 0;
    var output = "";

    if (fname == null || lname == null || phone == null){
        output = "<h4>Enter all mandatory elements!</h4>";
    }
    else {
        data.payees.push(["x", "x", fname, lname, phone, ""]);
        output = "<h4>Successfully Saved!</h4>";
    };
    document.getElementById("X").innerHTML="'HELLO";//output;
};

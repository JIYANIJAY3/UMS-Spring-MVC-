$(document).ready(function() {

    var count = 0;
    var isError = false;



    $(".add-btn").click(function() {
        count++;
        console.log("count " + count);

        var regexpincode = /(^[0-9]*$)/;
        var regescountry = /^[a-zA-Z\s]+$/;
        var regexstate = /^[a-zA-Z\s]+$/;
        var regexcity = /^[a-zA-Z\s]+$/;
        var regexaddress = /^[a-zA-Z\s]+$/;
        for (let i = 1; i <= count; i++) {


            $("#country_" + i).focusout(function() {
                var country = $(this).val();
                console.log(country)
                if (!country.match(regescountry)) {
                    console.log("here");
                    isError = true;
                    $('#country_' + i).after('<p>Phone number has to be 10 digits long.</p>')
                    console.log("Error " + isError)
                } else {
                    isError = false;
                    console.log(isError)
                }
            })

            $("#state_" + i).keyup(function() {
                var state = $(this).val();
                console.log(state)
                if (!state.match(regexstate)) {
                    console.log("here");
                    isError = true;
                    console.log("Error " + isError)
                } else {
                    isError = false;
                    console.log(isError)
                }
            })

            $("#city_" + i).keyup(function() {
                var city = $(this).val();
                console.log(city)
                if (!city.match(regexcity)) {
                    console.log("here");
                    isError = true;
                    console.log("Error " + isError)
                } else {
                    isError = false;
                    console.log(isError)
                }
            })

            $("#pincode_" + i).keyup(function() {
                var pincode = $(this).val();
                console.log(pincode)
                if (!pincode.match(regexpincode)) {
                    console.log("here");
                    isError = true;
                    console.log("Error " + isError)
                } else {
                    isError = false;
                    console.log(isError)
                }
            })

            $("#address_" + i).keyup(function() {
                var address = $(this).val();
                console.log(address)
                if (!address.match(regexaddress)) {
                    console.log("here");
                    isError = true;
                    console.log("Error " + isError)
                } else {
                    isError = false;
                    console.log(isError)
                }
            })
        }
    })

    $('.remove-btn').click(function() {
        count--;
        console.log("remove count " + count)
    })

    $.validator.addMethod('regex', function(value, element, regex) {
        return this.optional(element) || regex.test(value);
    }, "Formate Not Match");

    /*	$.validator.addMethod(
    		"Date",
    		function(value, element) {
    			return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
    		},
    		"Please enter a date in the format dd/mm/yyyy."
    	);*/
    //var count = $(".image-uploader").children().find($(".uploaded-image")).length;

    $(":file").change(function() {
        var count = $(".image-uploader").children().find($(".uploaded-image")).length;
        if (count > 0) {
            $(".image-error").hide();
        }
        console.log(count);
    });

    $("#submit-btn").click(function() {

        var count = $(".image-uploader").children().find($(".uploaded-image")).length;
        if (count == 0) {
            $(".image-error").show().css("color", "red");
            isError = true;
        } else {
            $(".image-error").hide();
            isError = false;
        }
        $("#form").validate({
            onfocusout: function(e) {
                this.element(e);
            },
            rules: {
                'userProfile.profiles[]': {
                    required: true,
                    accept: "image/*"
                },
                firstName: {
                    required: true,
                    minlength: 2,
                    lettersonly: true,
                },
                lastName: {
                    required: true,
                    minlength: 2,
                    lettersonly: true
                },
                dob: {
                    required: true,
                },
                gender: "required",
                email: {
                    required: true,
                    regex: /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/
                },
                password: {
                    required: true,
                    minlength: 8
                },
                answer: "required",
                mobaileNo: {
                    required: true,
                    digits: true,
                    minlength: 10,
                    maxlength: 10
                },
                language: {
                    required: true
                },
                'userAddress[0].country': {
                    required: true,
                    lettersonly: true
                },
                'userAddress[0].state': {
                    required: true,
                    lettersonly: true
                },
                'userAddress[0].city': {
                    required: true,
                    lettersonly: true
                },
                'userAddress[0].pinCode': {
                    required: true,
                    digits: true,
                    minlength: 6,
                    maxlength: 6
                },
                'userAddress[0].address': {
                    required: true
                }
            },
            messages: {
                firstName: {
                    required: "* FirstName Is Required",
                    minlength: "Minimun length is 2",
                    lettersonly: "* Enter Only Character"
                },
                lastName: {
                    required: "* LastName Is Required",
                    minlength: "Minimun length is 2",
                    lettersonly: "* Enter Only Character"
                },
                gender: "* Plese Select One",
                email: {
                    required: "* Email Is Required",
                    email: "* Enter Valide Email",
                    regex: "Enter Valid Email Formate Enter Email like xxx@xxx.xxx"
                },
                password: {
                    required: "* Password Is Required",
                    minlength: "* Minimum Length Is 8"
                },
                answer: "Fill This Field",
                mobaileNo: {
                    required: "* Mobail Number Is Required",
                    digits: "* Enter Only Digits"
                },
                language: {
                    required: "* Select atleast One"
                },
                country: {
                    required: "* Enter Country Name",
                    lettersonly: "* Enter Only Character"
                },
                'userAddress[0].state': {
                    required: "* Enter State Name",
                    lettersonly: "* Enter Only Character"
                },
                'userAddress[0].city': {
                    required: "* Enter City Name",
                    lettersonly: "* Enter Only Character"
                },
                'userAddress[0].pinCode': {
                    required: "* PinCode Is Required",
                    digits: "* Enter Only Digits"
                },
                'userAddress[0].address': {
                    required: "* Enter Address"
                }
            },
            errorPlacement: function(error, element) {
                if (element.is(":radio")) {
                    // error append here
                    error.appendTo('#radio');
                } else if (element.is(":checkbox")) {
                    error.appendTo('#checkbox');
                } else {
                    error.insertAfter(element);
                }
            },
            submitHandler: function(form, event) {
                event.preventDefault();
                if (isError == true) {
                    swal.fire("Somthing Went Wrong please fill the all details", "You clicked the submit!", "warning");
                } else {
                    let formdata = new FormData(form);
                    $("#form").hide();
                    $(".loader").show();

                    $.ajax({
                        type: "POST",
                        url: "submitform",
                        data: formdata,
                        enctype: 'multipart/form-data',

                        success: function(response) {
                            $("#form").show();
                            $(".loader").hide();
                            if (response.trim() === "Successfully Added...") {
                                swal.fire("Successfully Added", "You clicked the submit!", "success");
                            } else {
								$("#error-box").show();
								$("#error-msg").html(response);
                                swal.fire("Somthing Went Wrong please fill the all details", "You clicked the submit!", "warning");
                            }
                        },
                        error: function() {
                            $("#form").show();
                            $(".loader").hide();
                            $("#massage").html("Somthing Went Wrong... :( ").css("color", "red");
                        },
                        processData: false,
                        contentType: false
                    });
                }
            },
        })
    })
});
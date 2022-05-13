$(document).ready(function() {

	$("#emailAddress").change(function(e) {
		e.preventDefault();

		var email = $("#emailAddress").val();
		let formdata = new FormData($("#form")[0]);

		$.ajax({
			type: "POST",
			url: "CheckEmailIsPresent",
			data: formdata,
			enctype: 'multipart/form-data',

			success: function(data) {
				if (data.trim() === "done") {
					$("#isEmailPresent").html("Email Already Present").css("color", "red");
				} else {
					$("#isEmailPresent").html(" ");
				}
			},
			error: function(textStatus) {
				swal.fire("Somthing Went Wrong", " ", "warning");
			},
			processData: false,
			contentType: false
		});
	});
})
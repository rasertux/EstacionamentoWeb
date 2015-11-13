<%@ attribute name="id" required="true"%>

<input id="${id}" name="${id}" size="16" maxlength="16"
	readonly />

<script>
	$(function() {
		$("#${id}").datetimepicker({
			format : "dd/mm/yyyy hh:ii",
			autoclose : true,
			todayBtn : true,
			language : "pt-BR",
			pickerPosition : 'bottom-right',
		});
	});
</script>
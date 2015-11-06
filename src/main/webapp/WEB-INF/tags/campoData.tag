<%@ attribute name="id" required="true"%>

<input id="${id}" name="${id}" />

<script>
	$(function() {
		$("#${id}").datetimepicker({
			locale: 'pt-BR'
		});
	});
</script>
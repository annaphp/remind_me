// datepicker script 
    $(document).ready(function(){
      var date_input=$('input[name="dueDate"]'); //our date input has the name "dueDate"
      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      var options={
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
    })

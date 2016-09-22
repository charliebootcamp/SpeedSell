var selectedObjectType;
var initData;
var existDate;
var lastPhoneIndex = 0;
var initCheckboxesExecuteItems = false;

$(document).ready(

function () {
    // set focus on first input
    var val = $("input:text:visible:first").val();
    $("input:text:visible:first").val('');
    $("input:text:visible:first").val(val);
    $("input:text:visible:first").focus();

    $.validator.addMethod('BatchJobDateTime', function (value, element) {
        var $startDate = $('input#startDate');
        var $startTime = $('input#startTime');
        var $scheduleType = $('select#schedule\\.type');

        if ($scheduleType.val() !== "ONCE") return true;

        var dt = new Date($startDate.val() + " " + $startTime.val()).getTime();
        var currentDate = new Date().getTime();

        return dt > currentDate;

    }, "Batch Job date time can't be less than current date.");

    // reload page every 10 min...
    window.setTimeout(function () {
        document.location.reload(true);
    }, 900000);

    // init grids
    initBasicGrids();

    $('.img.state.STRIKEIRON_EXCEPTION').each(function () {
        // hack
        $(this).removeClass("STRIKEIRON_EXCEPTION");
        $(this).addClass("STRIKEIRON_EXCEPTION");
    });

    $('.timepckr').timepicker({
        showMeridian: true
    });
    $('.datepicker').datepicker();

    $('#empty-configuration-ico').each(function () {
        // $(this).tooltip({ position: "top center", opacity:
        // 0.7 } );$(this).tooltip({ position: "top center",
        // opacity: 0.7 } );
        // $(this).data('tooltip').show();
    });

    $('#cbGridSelectAll').each(function () {
        $(this).bind('click', function () {
            var cb = $(this);
            var checked = cb.is(':checked');
            $('.result-rows1 input[type=checkbox]').each(function () {
                $(this).attr('checked', checked);
            });
            forCorrectionGridDisableAcceptWhenNoSelected();
        });
    });

    $('.objectCheck').each(function () {
        $(this).bind('change', function () {
            forCorrectionGridDisableAcceptWhenNoSelected();
        });
    });

    $('.result-rows1 tr').each(function () {
        $('.clickable', $(this)).each(function () {
            $(this).css('cursor', 'pointer');
            $(this).bind('click', function () {
                var $parent = $(this).closest('tr');
                var id = $parent.find('.objectId').val();
                var resultTr = {};
                $('.result-rows tr').each(function () {
                    var id1 = $(this).attr('data-id');
                    if (id == id1) {
                        resultTr = $(this);
                    }
                });
                $('td:first-child', resultTr).trigger('click');
            });
        });

        forCorrectionGridDisableAcceptWhenNoSelected();

    });

    $('.j_filter_exclude_items input[type=checkbox]').each(

    function () {
        $(this).bind('change', function (e) {

            var cb = $(e.target);
            var valueControl = cb.parent().find('.value-control');
            if (!cb.is(':checked')) {
                valueControl.attr('disabled', 'disabled');
                valueControl.val('');
                cb.closest('.error').removeClass('error');
            } else {
                valueControl.removeAttr('disabled');
                if (valueControl.val() == undefined || valueControl.val() == '') {
                    valueControl.val('1');
                }
            };
            if (initCheckboxesExecuteItems) {
                $(".formWithTabs").valid();
            }
        });

        var v = $(this).parent().find('.value-control').val();
        if (v == undefined || v == "") {
            $(this).attr('checked', true);
        }
        $(this).trigger('click');

    });
    initCheckboxesExecuteItems = true;

    $('.formWithTabs').each(

    function () {
        var $this = $(this);
        var $formId = $this.attr('id');
        var elementId = $('#object\\.id').val();
        var elementName = $('#object\\.name').val();

        var removeBtn = $('#removeButton');
        if (removeBtn[0] != undefined) {
            if (elementId != '') {
                setupDeleteButton($(this).attr('data'), elementId, elementName.replace('\\', '/'), removeBtn.attr('data'));
            }
        };

        $('#submitButton').unbind('click').bind('click', function (e) {
            var $form = $(
            e.target).closest("form");
            if ($form.valid()) {
                var disableButton = $form.attr('disableButtonsOnSubmit');
                if (disableButton == undefined || disableButton == "true") {
                    $('.btn').attr("disabled", "disabled");
                }
                $form.submit();
                return true;
            }
            return false;
        });

        $this.unbind('submit').bind('submit', function () {
            $(this).find('input:text').each(

            function () {
                $(
                this).val(
                $.trim($(
                this).val()));
            });
        });

        try {
            $('#objectForm').dirtyForms();
        } catch (e) {
            alert(e);
        }
        $('#objectForm').validate();
    });

    $('.j_schedule_type').each(function () {
        $this = $(this);

        $this.bind('change', function () {
            changeScheduleType($(this));
        });

        // initialization
        changeScheduleType($this, true);
    });

    $('.j_select_object_type').each(function () {
    	initMapping($(this));
    });

    $('.j_mapping_select').each(

    function () {
        initMappingDropDownFromDataItemsAttr($(this), $('.j_select_object_type').val());
    });

    $('.j_new_Tab').each(function () {
        var tabContainer;
        if ($(this).is('.mapping-fields')) {
            tabContainer = $('#TabMappingFields-ajaxContent');
        } else if ($(this).is('.filter-fields')) {
            tabContainer = $('#TabFilter-ajaxContent');
        };

        if (tabContainer.length > 0) {
            tabContainer.empty().append($(this));
            $(this).show();
        }

    });

    // filtering
    initFormForFilters();
    $('#newFilterItem').each(function () {
        $(this).unbind('click').bind('click', function (e) {
            cloneBJFilterItem();
            hideLastLogicalOperationSelect();
            return false;
        });
    });

    // batch job details form
    /*
     * $('#batchJobDetailsForm').each(function() {
     * updateBatchJobDetails(); });
     */

    // batch job details form
    if ($('#batchJobStatistics').length == 1) {
        var form = new BatchDetailForm();
        form.init();
    }

});

function changeScheduleType(e, init) {
    var v = e.val();
    if (v == 'NEVER') {
        $('.j_schedule_params').css('display', 'none');
    } else {
        $('.j_schedule_params').css('display', 'block');
        if (!init) {
            hideAllScheduleTypeItem();
        }
        if (v == 'DAILY') {
            $('.j_schedule_daily').css('display', 'block');
        }
        if (v == 'WEEKLY') {
            $('.j_schedule_weekly').css('display', 'block');
        }
        if (v == 'MONTHLY') {
            $('.j_schedule_monthly').css('display', 'block');
        }
        if (v == 'YEARLY') {
            $('.j_schedule_yearly').css('display', 'block');
        }
    }
};

function hideAllScheduleTypeItem() {
    $('.day').val(1);
    $('.dayofmonth').val(1);
    $('.hundredyears').val(1);
    $('.j_schedule_daily').css('display', 'none');
    $('.j_schedule_weekly').css('display', 'none');
    $('.j_schedule_monthly').css('display', 'none');
    $('.j_schedule_yearly').css('display', 'none');
}

function setupDeleteButton(entityTitle, objectId, objectname, link) {
    $('#removeButton').click(

    function () {
        confirmDelete(entityTitle, objectId, objectname, link ? link : 'delete');
    });
}

function confirmDelete(prefix, id, name, action) {
    bootbox.confirm("You are going to delete the " + htmlEscape(prefix) + " named <strong>" + htmlEscape(name) + "</strong>. Are you sure?", function (result) {
        if (result) {
            var delForm = $('<form action="' + action + '" method="post">' + '<input name="id" type="hidden" value="' + id + '" />' + '<input name="CSRFToken" type="hidden" value="' + $('input[name="CSRFToken"]').val() + '" />' + '</form>');
            $('body').append(delForm);
            delForm.submit();
        }
    });
    return false;
}
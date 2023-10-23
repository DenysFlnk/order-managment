<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/header.jsp"/>

<body onload="loadContent()" class="d-flex flex-column h-100">
<jsp:include page="fragments/body-header.jsp"/>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/order-detail.js" defer></script>
<script type="text/javascript" src="resources/js/email-form.js" defer></script>

<br/>
<h2 class="text-center" id="orderNumber"></h2>
<div class="container">
    <form class="grey-background" id="orderForm">
        <input type="hidden" id="id" name="id">
        <h5><spring:message code="orders.modal.customerInfo"/></h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="customerName"><spring:message code="common.name"/></label>
                <input type="text" class="form-control" id="customerName" name="customerName"
                       placeholder="Example E.E.">
            </div>
            <div class="form-group col-md-4">
                <label for="customerPhone"><spring:message code="common.phone"/></label>
                <input type="text" class="form-control" id="customerPhone" name="customerPhone"
                       placeholder="+38(***)***-**-**">
            </div>
            <div class="form-group col-md-4">
                <label for="customerEmail"><spring:message code="common.email"/></label>
                <input type="text" class="form-control" id="customerEmail" name="customerEmail"
                       placeholder="example@example.com">
            </div>
        </div>
        <h5><spring:message code="orders.modal.orderInfo"/></h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="creationDate"><spring:message code="orders.creation"/></label>
                <input type="date" class="form-control" id="creationDate" name="creationDate">
            </div>
            <div class="form-group col-md-4">
                <label for="deliveryDate"><spring:message code="orders.delivery"/></label>
                <input type="date" class="form-control" id="deliveryDate" name="deliveryDate">
            </div>
            <div class="form-group col-md-3">
                <label for="orderStatus"><spring:message code="order-detail.orderStatus"/></label>
                <select class="form-control" id="orderStatus" name="orderStatus">
                    <option value="IN_WORK">
                        <spring:message code="order-detail.orderStatus.inWork"/>
                    </option>
                    <option value="COMPLETED">
                        <spring:message code="order-detail.orderStatus.completed"/>
                    </option>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="prepaid"><spring:message code="orders.prepaid"/></label>
                <input type="number" class="form-control" id="prepaid" name="prepaid" placeholder="0">
            </div>
            <div class="form-group col-md-4">
                <label for="surcharge"><spring:message code="orders.table.surcharge"/></label>
                <input type="number" class="form-control" id="surcharge" name="surcharge" placeholder="0" readonly>
            </div>
            <div class="form-group col-md-4">
                <label for="summaryCost"><spring:message code="orders.table.cost"/></label>
                <input type="number" class="form-control" id="summaryCost" name="summaryCost" placeholder="0" readonly>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-12">
                <label for="note"><spring:message code="orders.notes"/></label>
                <textarea class="form-control" id="note" name="note" placeholder="some notes..." rows="2"></textarea>
            </div>
        </div>

        <div class="modal-footer">
            <button type="button" class="btn btn-secondary btn-lg" onclick="location.href='orders'">
                <span class="fa fa-close"></span>
                <spring:message code="common.cancel"/>
            </button>
            <button type="button" class="btn btn-success btn-lg" onclick="saveOrder()">
                <span class="fa fa-check"></span>
                <spring:message code="common.save"/>
            </button>
        </div>
    </form>
    <div class="grey-background">
        <div class="modal-footer d-flex justify-content-start">
            <h5><spring:message code="order-detail.documents"/></h5>
            <button type="button" class="btn btn-success" onclick="openDocumentModal()">
                <span class="fa fa-plus"></span>
            </button>
        </div>
        <div class="form-row">
            <table class="table" id="docTable">
                <thead>
                <tr>
                    <th hidden="hidden" scope="col">Id</th>
                    <th scope="col"><spring:message code="order-detail.hardComplexity"/></th>
                    <th scope="col"><spring:message code="translators.language"/></th>
                    <th scope="col"><spring:message code="order-detail.officeRate"/></th>
                    <th scope="col"><spring:message code="translators.signs"/></th>
                    <th scope="col"><spring:message code="order-detail.notarizationCost"/></th>
                    <th scope="col"><spring:message code="order-detail.officeCost"/></th>
                    <th scope="col" colspan="2"><spring:message code="order-detail.translatorName"/></th>
                    <th scope="col"><spring:message code="order-detail.translatorRate"/></th>
                    <th scope="col"><spring:message code="order-detail.translatorTax"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody id="docTableBody">
                </tbody>
            </table>
        </div>

        <div class="modal-footer d-flex justify-content-start">
            <h5><spring:message code="order-detail.apostilles"/></h5>
            <button type="button" class="btn btn-success" onclick="openModal('aposModal')">
                <span class="fa fa-plus"></span>
            </button>
        </div>
        <div class="form-row">
            <table class="table" id="aposTable">
                <thead>
                <tr>
                    <th hidden="hidden" scope="col">Id</th>
                    <th scope="col"><spring:message code="order-detail.title"/></th>
                    <th scope="col"><spring:message code="order-detail.subCountry"/></th>
                    <th scope="col"><spring:message code="order-detail.subDepartment"/></th>
                    <th scope="col"><spring:message code="orders.table.cost"/></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody id="aposTableBody">
                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="modal fade" tabindex="-1" id="docModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="order-detail.document"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="docForm">
                    <input type="hidden" id="documentId" name="id">
                    <div id="appendPlaceCheckbox">
                        <div class="form-group" id="checkBoxGroup">
                            <label for="isHardComplexity" class="col-form-label">
                                <spring:message code="order-detail.hardComplexity"/>
                            </label>
                            <input type="checkbox" id="isHardComplexity" name="isHardComplexity">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="documentLanguage" class="col-form-label">
                                <spring:message code="translators.language"/> *
                            </label>
                            <select class="form-control" id="documentLanguage" name="language">
                                <option value="" disabled selected>
                                    <spring:message code="translators.languageSelect"/>
                                </option>
                                <option>ALBANIAN</option>
                                <option>BASQUE</option>
                                <option>BULGARIAN</option>
                                <option>CATALAN</option>
                                <option>CROATIAN</option>
                                <option>CZECH</option>
                                <option>DANISH</option>
                                <option>DUTCH</option>
                                <option>ENGLISH</option>
                                <option>ESTONIAN</option>
                                <option>FINNISH</option>
                                <option>FRENCH</option>
                                <option>GERMAN</option>
                                <option>GREEK</option>
                                <option>HUNGARIAN</option>
                                <option>IRISH</option>
                                <option>ITALIAN</option>
                                <option>LATVIAN</option>
                                <option>LITHUANIAN</option>
                                <option>MALTESE</option>
                                <option>NORWEGIAN</option>
                                <option>POLISH</option>
                                <option>PORTUGUESE</option>
                                <option>ROMANIAN</option>
                                <option>RUSSIAN</option>
                                <option>SERBIAN</option>
                                <option>SLOVAK</option>
                                <option>SLOVENIAN</option>
                                <option>SPANISH</option>
                                <option>SWEDISH</option>
                                <option>TURKISH</option>
                                <option>UKRAINIAN</option>
                                <option>WELSH</option>
                                <option>CHINESE</option>
                                <option>JAPANESE</option>
                                <option>KOREAN</option>
                                <option>HINDI</option>
                                <option>ARABIC</option>
                                <option>URDU</option>
                                <option>BENGALI</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="officeRate" class="col-form-label">
                                <spring:message code="order-detail.officeRate"/> *
                            </label>
                            <input type="number" class="form-control" id="officeRate" name="officeRate"
                                   placeholder="0">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="signsNumber" class="col-form-label">
                            <spring:message code="translators.signs"/>
                        </label>
                        <input type="number" class="form-control" id="signsNumber" name="signsNumber"
                               placeholder="0">
                        <label for="notarizationCost" class="col-form-label">
                            <spring:message code="order-detail.notarizationCost"/>
                        </label>
                        <input type="number" class="form-control" id="notarizationCost" name="notarizationCost"
                               placeholder="0" value="0">
                        <label for="officeCost" class="col-form-label">
                            <spring:message code="order-detail.officeCost"/>
                        </label>
                        <input type="number" class="form-control" id="officeCost" name="officeCost"
                               placeholder="0">
                    </div>
                    <br/>
                    <div class="form-group">
                        <h5><spring:message code="translators.translator"/>
                            <button type="button" class="btn btn-warning" onclick="showTranslatorsFor()">
                                <span class="fa fa-ellipsis"></span>
                            </button>
                        </h5>
                        <input type="hidden" id="translatorId" name="translatorId">
                        <label for="translatorName" class="col-form-label"><spring:message code="common.name"/></label>
                        <input type="text" class="form-control" id="translatorName" name="translatorName" readonly>
                        <label for="translatorRate" class="col-form-label">
                            <spring:message code="translators.rate"/>
                        </label>
                        <input type="text" class="form-control" id="translatorRate" name="translatorRate">
                        <label for="translatorTax" class="col-form-label">
                            <spring:message code="order-detail.tax"/>
                        </label>
                        <input type="text" class="form-control" id="translatorTax" name="translatorTax">
                    </div>
                </form>
                <h6><spring:message code="common.required"/></h6>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveDocument()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="aposModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="order-detail.apostille"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="aposForm">
                    <input type="hidden" id="apostilleId" name="id">
                    <div class="form-group">
                        <label for="title" class="col-form-label"><spring:message code="order-detail.title"/> *</label>
                        <input type="text" class="form-control" id="title" name="title"
                               placeholder="'Title'">
                    </div>

                    <div class="form-group">
                        <label for="submissionCountry" class="col-form-label">
                            <spring:message code="order-detail.subCountry"/> *
                        </label>
                        <input type="text" class="form-control" id="submissionCountry" name="submissionCountry"
                               placeholder="Ukraine">
                    </div>

                    <div class="form-group">
                        <label for="submissionDepartment" class="col-form-label">
                            <spring:message code="order-detail.subDepartment"/> *
                        </label>
                        <input type="email" class="form-control" id="submissionDepartment" name="submissionDepartment"
                               placeholder="Department">
                    </div>
                    <div class="form-group">
                        <label for="cost" class="col-form-label"><spring:message code="orders.table.cost"/> *</label>
                        <input type="number" class="form-control" id="cost" name="cost"
                               placeholder="0">
                    </div>
                </form>
                <h6><spring:message code="common.required"/></h6>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveApostille()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="changeTranslator">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="order-detail.transaltors"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <table class="table" id="translatorTable">
                    <thead>
                    <tr>
                        <th hidden="hidden" scope="col">Id</th>
                        <th scope="col"><spring:message code="common.name"/></th>
                        <th scope="col"><spring:message code="common.email"/></th>
                        <th scope="col"><spring:message code="translators.language"/></th>
                        <th scope="col"><spring:message code="translators.rate"/></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody id="translatorTableBody">
                    </tbody>
                </table>
                <div class="d-flex justify-content-center">
                    <ul class="pagination" id="translatorPagination">
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="email-form.jsp"/>
<jsp:include page="fragments/i18n.jsp"/>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
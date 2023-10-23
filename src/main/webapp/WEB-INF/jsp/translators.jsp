<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/header.jsp"/>

<body onload="loadContent(0)" class="d-flex flex-column h-100">
<jsp:include page="fragments/body-header.jsp"/>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/translators.js" defer></script>
<br/>
<h2 class="text-center"><spring:message code="translators.list"/></h2>
<br/>
<div class="container">
    <button class="btn btn-success btn-lg" onclick="openModal('translatorModal')">
        <span class="fas fa-plus fa-lg"></span>
        <spring:message code="translators.add"/>
    </button>
</div>
<br/>
<div class="container">
    <table class="table table-bordered table-inc-font" id="translatorTable">
        <thead>
        <tr>
            <th><spring:message code="translators.available"/></th>
            <th><spring:message code="common.name"/></th>
            <th><spring:message code="common.email"/></th>
            <th><spring:message code="common.phone"/></th>
            <th><spring:message code="translators.languages"/></th>
            <th><spring:message code="translators.rates"/></th>
            <th><span class="fa fa-ellipsis"></span></th>
            <th><span class="fa fa-ellipsis"></span></th>
        </tr>
        </thead>
        <tbody id="translatorTableBody">
        </tbody>
    </table>
</div>
<div class="d-flex justify-content-center">
    <ul class="pagination" id="translatorPagination">
    </ul>
</div>
<div class="modal fade" tabindex="-1" id="translatorModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="translators.translator"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="translatorForm">
                    <input type="hidden" id="translatorId" name="id">
                    <input type="hidden" id="available" name="available">
                    <div class="form-group">
                        <label for="translatorName" class="col-form-label"><spring:message code="common.name"/> *</label>
                        <input type="text" class="form-control" id="translatorName" name="name"
                               placeholder="John">
                    </div>
                    <div class="form-group">
                        <label for="translatorEmail" class="col-form-label"><spring:message code="common.email"/> *</label>
                        <input  type="email" class="form-control" id="translatorEmail" name="email"
                                placeholder="john@gmail.com">
                    </div>
                    <div class="form-group">
                        <label for="translatorPhone" class="col-form-label"><spring:message code="common.phone"/></label>
                        <input type="text" step="0.1" class="form-control" id="translatorPhone" name="phoneNumber"
                               placeholder="+380xxxxxxx">
                    </div>
                </form>
                <h6><spring:message code="common.required"/></h6>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveTranslator()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="rateModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="translators.rates"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <div class="container">
                    <button type="button" class="btn btn-success" onclick="openModal('editRate')">
                        <span class="fa fa-plus"></span>
                        <spring:message code="translators.rate"/>
                    </button>
                </div>
                <br/>
                <div class="container">
                    <table class="table table-bordered" id="rateTable">
                        <thead>
                        <tr>
                            <th><spring:message code="translators.language"/></th>
                            <th><spring:message code="translators.common"/></th>
                            <th><spring:message code="translators.hard"/></th>
                            <th><spring:message code="translators.signs"/></th>
                            <th><span class="fa fa-ellipsis"></span></th>
                            <th><span class="fa fa-ellipsis"></span></th>
                        </tr>
                        </thead>
                        <tbody id="rateTableBody">
                        </tbody>
                    </table>
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
<div class="modal fade" tabindex="-1" id="editRate">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="translators.rates"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="rateForm">
                    <input type="hidden" id="rateId" name="id">
                    <div class="form-group">
                        <label for="rateLanguage" class="col-form-label">
                            <spring:message code="translators.language"/> *
                        </label>
                        <select class="form-control" id="rateLanguage" name="language">
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
                    <div class="form-group">
                        <label for="commonRate" class="col-form-label"><spring:message code="translators.common"/> *</label>
                        <input type="number" class="form-control" id="commonRate" name="commonRate"
                               placeholder="0">
                    </div>
                    <div class="form-group">
                        <label for="hardRate" class="col-form-label"><spring:message code="translators.hard"/> *</label>
                        <input  type="number" class="form-control" id="hardRate" name="hardRate"
                                placeholder="0">
                    </div>
                    <div class="form-group">
                        <label for="signs" class="col-form-label"><spring:message code="translators.signs"/> *</label>
                        <input type="number" step="0.1" class="form-control" id="signs" name="signs"
                               placeholder="0.1">
                    </div>
                </form>
                <h6><spring:message code="common.required"/></h6>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveRate()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/i18n.jsp"/>
<jsp:include page="fragments/footer.jsp"/>
</body>
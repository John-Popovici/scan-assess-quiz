package org.uni.lu.quizselectorgame.repository.questions;

public class QuestionJson {
    private String serviceCategory;
    private String section;
    private String qType;
    private Integer qIndex;
    private AnswerJson answer;
    private String label;

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getQType() {
        return qType;
    }

    public void setQType(String qType) {
        this.qType = qType;
    }

    public Integer getQIndex() {
        return qIndex;
    }

    public void setQIndex(Integer qIndex) {
        this.qIndex = qIndex;
    }

    public AnswerJson getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerJson answer) {
        this.answer = answer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

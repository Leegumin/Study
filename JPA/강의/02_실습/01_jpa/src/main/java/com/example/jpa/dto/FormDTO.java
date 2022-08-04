package com.example.jpa.dto;

/**
 * packageName : com.example.jpa.dto
 * fileName : FormDTO
 * author : Mingu
 * date : 2022-08-04
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-04         Mingu          최초 생성
 */
public class FormDTO {
    private String id;
    private String name;
    private String email;
    // * 빈 값으로 들어올때 long형은 기본값이 0으로 들어오기 때문에 null도 받을 수 있는 LONG로 설정
    private Long   age;
    private String gender;

    // * 나중에 롬북으로 쓰면 되지만 일단은 getter, setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // 멤버변수를 확인하기 위한 toString
    @Override
    public String toString() {
        return "FormDTO{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", age=" + age +
               ", gender='" + gender + '\'' +
               '}';
    }
}

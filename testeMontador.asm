# Exemplo de teste

main:
    add $t0, $t1, $t2
    addi $t1, $t0, 10
    sub $t2, $t1, $t0
    mul $t3, $t1, $t2

    beq $t0, $t1, label1
    add $t0, $t0, $t0

label1:
    lw $t4, 0($t0)
    sw $t4, 4($t0)

    j fim

fim:
    add $t0, $t0, $t0
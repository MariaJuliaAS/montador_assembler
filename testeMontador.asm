# TESTE COMPLETO MIPS

inicio:
    add $t0, $t1, $t2
    sub $t2, $t0, $t1
    and $t3, $t0, $t2
    or  $t4, $t3, $t0

    sll $t5, $t0, 2
    srl $t6, $t5, 1

    addi $t0, $t0, 5
    slti $t1, $t0, 20

    mult $t0, $t1
    mfhi $s0
    mflo $s1

loop:
    lw $t2, 0($t0)
    sw $t2, 4($t0)

    beq $t0, $t1, fim
    addi $t0, $t0, 1

    j loop

fim:
    jr $ra